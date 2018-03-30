package com.ecgobike.controller;

import com.ecgobike.common.constant.Constants;
import com.ecgobike.pojo.request.AuthParams;
import com.ecgobike.pojo.request.JoinMembershipParams;
import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.enums.OrderType;
import com.ecgobike.common.exception.GException;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.entity.EBike;
import com.ecgobike.entity.OrderMembership;
import com.ecgobike.entity.User;
import com.ecgobike.pojo.request.RenewParams;
import com.ecgobike.pojo.request.Money;
import com.ecgobike.pojo.response.MessageDto;
import com.ecgobike.service.EBikeService;
import com.ecgobike.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ebike")
public class EBikeController {

    @Autowired
    EBikeService eBikeService;

    @Autowired
    UserService userService;

    @PostMapping("/list")
    @AuthRequire(Auth.USER)
    public MessageDto list(AuthParams authParams) {
        List<EBike> ebikes =  eBikeService.findAllByUid(authParams.getUid());
        Map<String, Object> data = new HashMap<>();
        data.put("ebikes", ebikes);
        return MessageDto.responseSuccess(data);
    }

    @PostMapping("/join")
    @AuthRequire(Auth.USER)
    public MessageDto join(JoinMembershipParams params) throws GException {
        // check ebike
        EBike ebike = eBikeService.findOneBySn(params.getEbikeSn());
        if (ebike == null) {
            throw new GException(ErrorConstants.NOT_EXIST_EBIKE);
        }
        if (ebike.getIsMembership() == 1) {
            throw new GException(ErrorConstants.ALREADY_MEMBERSHIP);
        }

        // check user money
        User user = userService.getUserByUid(params.getUid());
        BigDecimal money = user.getMoney();

        BigDecimal fee = Constants.MEMBERSHIP_FEE.add(Constants.MONTH_FEE);

        if (money.compareTo(fee) == -1) {
            throw new GException(ErrorConstants.LACK_MONEY);
        }

        OrderMembership order = eBikeService.joinMembership(ebike);
        user = userService.minusMoney(user, fee);

        Map<String, Object> data = new HashMap<>();
        data.put("balance", user.getMoney());
        data.put("order", order);
        return MessageDto.responseSuccess(data);
    }

    @PostMapping("/renew")
    @AuthRequire(Auth.USER)
    public MessageDto renew(RenewParams params) throws GException {
        // check ebike
        EBike ebike = eBikeService.findOneBySn(params.getEbikeSn());
        if (ebike == null) {
            throw new GException(ErrorConstants.NOT_EXIST_EBIKE);
        }
        if (ebike.getIsMembership() == 0) {
            throw new GException(ErrorConstants.NO_MEMBERSHIP);
        }
        if (ebike.getExpireDate() != null && ebike.getExpireDate().isAfter(LocalDate.now())) {
            throw new GException(ErrorConstants.ALREADY_RENEW);
        }

        // check user money

        User user = userService.getUserByUid(params.getUid());
        BigDecimal money = user.getMoney();
        if (money.compareTo(Constants.MONTH_FEE) == -1) {
            throw new GException(ErrorConstants.LACK_MONEY);
        }

        OrderMembership order = eBikeService.renew(ebike);
        user = userService.minusMoney(user, Constants.MONTH_FEE);

        Map<String, Object> data = new HashMap<>();
        data.put("balance", user.getMoney());
        data.put("order", order);
        return MessageDto.responseSuccess(data);
    }

}