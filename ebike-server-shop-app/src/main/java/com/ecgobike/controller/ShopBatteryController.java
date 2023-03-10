package com.ecgobike.controller;

import com.ecgobike.common.enums.ProductType;
import com.ecgobike.entity.*;
import com.ecgobike.pojo.request.LendBatteryParams;
import com.ecgobike.pojo.request.ProductParams;
import com.ecgobike.pojo.request.ReturnBatteryParams;
import com.ecgobike.pojo.response.BatteryVO;
import com.ecgobike.pojo.response.EBikeVO;
import com.ecgobike.pojo.response.LendBatteryVO;
import com.ecgobike.service.*;
import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.exception.GException;
import com.ecgobike.pojo.response.AppResponse;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChenJun on 2018/3/27.
 */
@RestController
@RequestMapping("/battery")
public class ShopBatteryController {

    @Autowired
    BatteryService batteryService;

    @Autowired
    StaffService staffService;

    @Autowired
    LendBatteryService lendBatteryService;

    @Autowired
    BookBatteryService bookBatteryService;

    @Autowired
    EBikeService eBikeService;

    @Autowired
    ProductService productService;

    @Autowired
    Mapper mapper;

    @RequestMapping("/info")
    @AuthRequire(Auth.STAFF)
    public AppResponse info(String batterySn) throws GException {
        Battery battery = batteryService.findOneBySn(batterySn);
        if (battery == null) {
            throw new GException(ErrorConstants.NOT_EXIST_BATTERY);
        }
        BatteryVO batteryVO = mapper.map(battery, BatteryVO.class);
        Map<String, Object> data = new HashMap<>();
        data.put("battery", batteryVO);
        return AppResponse.responseSuccess(data);
    }

    @RequestMapping("/return")
    @AuthRequire(Auth.STAFF)
    public AppResponse returnBattery(ReturnBatteryParams params) throws GException {
        String uid = params.getUid();
        String batterySn = params.getBatterySn();
        Staff staff = staffService.findOneByUid(params.getUid());
        batteryService.returnBattery(staff.getShop().getId(), params.getBatterySn());
        LendBattery lendBattery = lendBatteryService.returnBattery(staff, batterySn);
        LendBatteryVO lendBatteryVO = mapper.map(lendBattery, LendBatteryVO.class);
        Map<String, Object> data = new HashMap<>();
        data.put("lendBattery", lendBatteryVO);
        return AppResponse.responseSuccess(data);
    }

    @PostMapping("/lend")
    @AuthRequire(Auth.STAFF)
    public AppResponse lend(LendBatteryParams params) throws GException {
        String ebikeSn = params.getEbikeSn();
        EBike eBike = eBikeService.canLendBattery(ebikeSn);
        if (eBike.getUid() == null) {
            throw new GException(ErrorConstants.NOT_YOUR_EBIKE);
        }

        // check for battery
        Long shopId = staffService.getShopIdByUid(params.getUid());
        Battery battery = batteryService.canLend(params.getBatterySn(), ebikeSn, shopId);

        // check booking
        bookBatteryService.lendIfHasBooking(ebikeSn, battery.getSn());

        LendBattery lendBattery = lendBatteryService.lend(eBike, battery, params.getUid());
        batteryService.lend(eBike, battery);
        eBike = eBikeService.lendBattery(eBike);

        LendBatteryVO lendBatteryVO = mapper.map(lendBattery, LendBatteryVO.class);
        EBikeVO eBikeVO = mapper.map(eBike, EBikeVO.class);

        Map<String, Object> data = new HashMap<>();
        data.put("lendBattery", lendBatteryVO);
        data.put("ebike", eBikeVO);
        return AppResponse.responseSuccess(data);
    }

    @RequestMapping("/stock/list")
    @AuthRequire(Auth.STAFF)
    public AppResponse stockList(
            ProductParams params,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) throws GException {
        Product product = productService.getOne(params.getProductId());
        if (product == null) {
            throw new GException(ErrorConstants.NOT_EXIST_PRODUCT);
        }
        if (product.getType() != ProductType.BATTERY) {
            throw new GException(ErrorConstants.NOT_BATTERY_PRODUCT);
        }
        Long shopId = staffService.getShopIdByUid(params.getUid());
        Page<Battery> stockList = batteryService.findProductStockInShop(product, shopId, pageable);
        Page<BatteryVO> voList = stockList.map(battery -> mapper.map(battery, BatteryVO.class));
        Map<String, Object> data = new HashMap<>();
        data.put("stockList", voList);
        return AppResponse.responseSuccess(data);
    }
}
