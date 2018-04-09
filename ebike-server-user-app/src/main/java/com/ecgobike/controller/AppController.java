package com.ecgobike.controller;

import com.ecgobike.common.constant.Constants;
import com.ecgobike.pojo.response.MessageDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChenJun on 2018/3/15.
 */
@RestController
@RequestMapping("/app")
public class AppController {

    @RequestMapping("/setting")
    public MessageDto setting() {
        Map<String, Object> data = new HashMap<>();
        data.put("membershipFee", Constants.MEMBERSHIP_FEE);
        //data.put("monthFee", Constants.MONTH_FEE);
        data.put("currency", Constants.CURRENCY);
        data.put("currencySymbol", Constants.CURRENCY_SYMBOL);
        data.put("serviceAgreementUrl", "");
        Map<Integer, BigDecimal> map = new HashMap<>();
        map.put(1,  new BigDecimal(0.8));
        map.put(10, new BigDecimal(7));
        map.put(15, new BigDecimal(9));
        map.put(20, new BigDecimal(11));
        map.put(25, new BigDecimal(13));
        map.put(30, new BigDecimal(15));
        map.put(35, new BigDecimal(17));
        map.put(40, new BigDecimal(19));
        map.put(50, new BigDecimal(23));
        data.put("monthFeeMap", map);
        return MessageDto.responseSuccess(data);
    }
}
