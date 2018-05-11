package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.entity.BookBattery;
import com.ecgobike.pojo.request.AuthParams;
import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.pojo.response.BookBatteryVO;
import com.ecgobike.service.BookBatteryService;
import com.ecgobike.service.StaffService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ShopBookingController {

    @Autowired
    BookBatteryService bookBatteryService;

    @Autowired
    StaffService staffService;

    @Autowired
    Mapper mapper;

    @RequestMapping("/list")
    @AuthRequire(Auth.STAFF)
    public AppResponse list(AuthParams params) {
        Long shopId = staffService.getShopIdByUid(params.getUid());
        List<BookBattery> bookBatteryList = bookBatteryService.getByShopId(shopId);
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

}
