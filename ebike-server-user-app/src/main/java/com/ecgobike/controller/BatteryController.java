package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.*;
import com.ecgobike.pojo.request.AuthParams;
import com.ecgobike.pojo.request.BookBatteryParams;
import com.ecgobike.pojo.request.LendBatteryParams;
import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.pojo.response.BookBatteryVO;
import com.ecgobike.pojo.response.EBikeVO;
import com.ecgobike.pojo.response.LendBatteryVO;
import com.ecgobike.service.*;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/battery")
public class BatteryController {

    @Autowired
    EBikeService eBikeService;

    @Autowired
    BatteryService batteryService;

    @Autowired
    UserService userService;

    @Autowired
    LendBatteryService lendBatteryService;

    @Autowired
    ShopService shopService;

    @Autowired
    BookBatteryService bookBatteryService;

    @Autowired
    Mapper mapper;

    @PostMapping("/lend")
    @AuthRequire(Auth.USER)
    public AppResponse lend(LendBatteryParams params) throws GException {
        String ebikeSn = params.getEbikeSn();
        EBike eBike = eBikeService.canLendBattery(ebikeSn);
        if (eBike.getUid() == null || !eBike.getUid().equals(params.getUid())) {
            throw new GException(ErrorConstants.NOT_YOUR_EBIKE);
        }

        // check for battery
        Battery battery = batteryService.canLend(params.getBatterySn(), ebikeSn,null);

        LendBattery lendBattery = lendBatteryService.lend(eBike, battery, null);
        batteryService.lend(eBike, battery);
        eBike = eBikeService.lendBattery(eBike);
        LendBatteryVO lendBatteryVO = mapper.map(lendBattery, LendBatteryVO.class);
        EBikeVO eBikeVO = mapper.map(eBike, EBikeVO.class);
        Map<String, Object> data = new HashMap<>();
        data.put("lendBattery", lendBatteryVO);
        data.put("ebike", eBikeVO);
        return AppResponse.responseSuccess(data);
    }

    @PostMapping("/book")
    @AuthRequire(Auth.USER)
    public AppResponse book(BookBatteryParams params) throws GException {
        User user = userService.getUserByUid(params.getUid());
        String ebikeSn = params.getEbikeSn();
        EBike eBike = eBikeService.canLendBattery(ebikeSn);
        if (eBike.getUid() == null || !eBike.getUid().equals(params.getUid())) {
            throw new GException(ErrorConstants.NOT_YOUR_EBIKE);
        }
        List<BookBattery> list = bookBatteryService.getByEbikeSn(ebikeSn);
        if (list.size() > 0) {
            throw new GException(ErrorConstants.EBIKE_ALREADY_BOOKED);
        }
        Shop shop = shopService.getShopById(params.getShopId());
        if (shop == null) {
            throw new GException(ErrorConstants.NOT_EXIST_SHOP);
        }
        Long batteryAvailable = batteryService.countStockInShop(shop.getId());
        Long batteryBooked = bookBatteryService.countBookNumInShop(shop.getId());
        if (batteryAvailable <= batteryBooked) {
            throw new GException(ErrorConstants.NO_BATTERY_TO_BOOK);
        }
        shop.setBatteryAvailable(batteryAvailable.intValue());
        shop.setBatteryBooked(batteryBooked.intValue()+1);
        shopService.save(shop);
        BookBattery bookBattery = bookBatteryService.book(user, ebikeSn, shop);
        BookBatteryVO bookBatteryVO = mapper.map(bookBattery, BookBatteryVO.class);
        Map<String, Object> data = new HashMap<>();
        data.put("bookBattery", bookBatteryVO);
        return AppResponse.responseSuccess(data);
    }

    @RequestMapping("/book/list")
    @AuthRequire(Auth.USER)
    public AppResponse book(AuthParams params) throws GException {
        List<BookBattery> bookBatteryList = bookBatteryService.getByUid(params.getUid());
        List<BookBatteryVO> list = bookBatteryList.stream()
                .map(bookBattery -> mapper.map(bookBattery, BookBatteryVO.class))
                .collect(Collectors.toList());
        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        return AppResponse.responseSuccess(data);
    }
}
