package net.zriot.ebike.controller;

import net.zriot.ebike.common.constant.ErrorConstants;
import net.zriot.ebike.common.exception.GException;
import net.zriot.ebike.entity.User;
import net.zriot.ebike.pojo.request.Money;
import net.zriot.ebike.pojo.request.TopupParams;
import net.zriot.ebike.pojo.response.MessageDto;
import net.zriot.ebike.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ChenJun on 2018/3/27.
 */
@RestController
@RequestMapping("/money")
public class MoneyController {

    @Autowired
    UserService userService;

    @PostMapping("/topup")
    public MessageDto topup(TopupParams params) throws GException {
        User user = userService.getUserByTel(params.getTel());
        if (user == null) {
            throw new GException(ErrorConstants.USER_NOT_FOUND);
        }

        String currency = params.getCurrency() == null ? "USD" : params.getCurrency();
        Money money = new Money(params.getAmount(), currency);
        userService.addMoney(user, money);
        return MessageDto.responseSuccess();

    }
}
