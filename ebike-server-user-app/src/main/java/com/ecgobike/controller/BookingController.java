package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.BookBattery;
import com.ecgobike.entity.EBike;
import com.ecgobike.entity.Shop;
import com.ecgobike.entity.User;
import com.ecgobike.pojo.request.AuthParams;
import com.ecgobike.pojo.request.BookBatteryParams;
import com.ecgobike.pojo.request.BookCancelParams;
import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.pojo.response.BookBatteryVO;
import com.ecgobike.service.*;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by ChenJun on 2018/5/11.
 */
@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    BookBatteryService bookBatteryService;

    @Autowired
    UserService userService;

    @Autowired
    EBikeService eBikeService;

    @Autowired
    ShopService shopService;

    @Autowired
    BatteryService batteryService;

    @Autowired
    Mapper mapper;

    @PostMapping("/create")
    @AuthRequire(Auth.USER)
    public AppResponse create(BookBatteryParams params) throws GException {
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
        Duration duration = Duration.between(LocalDateTime.now(), bookBattery.getExpireTime());
        bookBatteryVO.setLeftSeconds(duration.getSeconds());
        Map<String, Object> data = new HashMap<>();
        data.put("bookBattery", bookBatteryVO);
        return AppResponse.responseSuccess(data);
    }

    @RequestMapping("/list")
    @AuthRequire(Auth.USER)
    public AppResponse list(AuthParams params) throws GException {
        List<BookBattery> bookBatteryList = bookBatteryService.getByUid(params.getUid());
        List<BookBatteryVO> list = bookBatteryList.stream().map(bookBattery -> {
            BookBatteryVO vo = mapper.map(bookBattery, BookBatteryVO.class);
            Duration duration = Duration.between(LocalDateTime.now(), bookBattery.getExpireTime());
            vo.setLeftSeconds(duration.getSeconds());
            return vo;
        }).collect(Collectors.toList());
        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        return AppResponse.responseSuccess(data);
    }

    @RequestMapping("/cancel")
    @AuthRequire(Auth.USER)
    public AppResponse cancel(BookCancelParams params) throws GException {
        BookBattery bookBattery = bookBatteryService.cancelBy(params.getBookId(), params.getUid());
        Shop shop = bookBattery.getShop();
        Long batteryAvailable = batteryService.countStockInShop(shop.getId());
        Long batteryBooked = bookBatteryService.countBookNumInShop(shop.getId());
        shop.setBatteryAvailable(batteryAvailable.intValue());
        shop.setBatteryBooked(batteryBooked.intValue());
        shopService.save(shop);
        return AppResponse.responseSuccess();
    }
}
