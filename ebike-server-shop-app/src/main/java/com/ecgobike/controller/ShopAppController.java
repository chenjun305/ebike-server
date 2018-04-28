package com.ecgobike.controller;

import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.pojo.response.AppSettingVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChenJun on 2018/4/28.
 */
@RestController
@RequestMapping("/app")
public class ShopAppController {
    
    @RequestMapping("/setting")
    public AppResponse setting() {
        Map<String, Object> data = new HashMap<>();
        AppSettingVO settingVO = AppSettingVO.getDefault();
        return AppResponse.responseSuccess(settingVO);
    }
}
