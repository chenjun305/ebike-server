package net.zriot.ebike.controller.app;

import net.zriot.ebike.common.constant.Constants;
import net.zriot.ebike.pojo.response.MessageDto;
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
    public MessageDto setting() {
        Map<String, Object> data = new HashMap<>();
        data.put("membershipFee", Constants.MEMBERSHIP_FEE);
        data.put("monthFee", Constants.MONTH_FEE);
        data.put("currency", Constants.CURRENCY);
        data.put("currencySymbol", Constants.CURRENCY_SYMBOL);
        data.put("serviceAgreementUrl", "");
        return MessageDto.responseSuccess(data);
    }
}
