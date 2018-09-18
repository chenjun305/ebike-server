package com.ecgobike.controller;

import com.ecgobike.pojo.ChargerDeviceParams;
import com.ecgobike.pojo.ChargerFullListParams;
import com.ecgobike.pojo.MsgResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Created by ChenJun on 2018/9/18.
 */
@RestController
@RequestMapping("/charger")
public class ChargerController {

    @PostMapping("/Device")
    public MsgResponse device(ChargerDeviceParams params) {
        System.out.println("/charger/Device:" + params.toString());
        return MsgResponse.responseSuccess();
    }

    @PostMapping("/Device.xhtml")
    public MsgResponse deviceXhtml(ChargerDeviceParams params) {
        System.out.println("/charger/Device.xhtml:" + params.toString());
        return MsgResponse.responseSuccess();
    }

    @PostMapping("/FullList")
    public MsgResponse fullList(ChargerFullListParams params) {
        System.out.println("/charger/FullList:" + params.toString());
        return MsgResponse.responseSuccess();
    }

    @PostMapping("/FullList.xhtml")
    public MsgResponse fullListXhtml(ChargerFullListParams params) {
        System.out.println("/charger/FullList.xhtml:" + params.toString());
        return MsgResponse.responseSuccess();
    }
}
