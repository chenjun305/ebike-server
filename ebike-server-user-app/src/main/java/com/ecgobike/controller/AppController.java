package com.ecgobike.controller;

import com.ecgobike.common.constant.Constants;
import com.ecgobike.common.constant.MonthNumFee;
import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.pojo.response.AppSettingVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChenJun on 2018/3/15.
 */
@RestController
@RequestMapping("/app")
public class AppController {

    @RequestMapping("/setting")
    public AppResponse setting() {
        Map<String, Object> data = new HashMap<>();
        AppSettingVO settingVO = AppSettingVO.getDefault();
        return AppResponse.responseSuccess(settingVO);
    }
}
