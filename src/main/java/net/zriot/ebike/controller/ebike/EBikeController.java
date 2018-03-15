package net.zriot.ebike.controller.ebike;

import net.zriot.ebike.common.annotation.AuthRequire;
import net.zriot.ebike.common.constant.Constants;
import net.zriot.ebike.common.enums.Auth;
import net.zriot.ebike.common.exception.GException;
import net.zriot.ebike.common.constant.ErrorConstants;
import net.zriot.ebike.entity.ebike.EBike;
import net.zriot.ebike.entity.order.OrderMembership;
import net.zriot.ebike.entity.order.OrderMonthPay;
import net.zriot.ebike.entity.user.User;
import net.zriot.ebike.pojo.request.AuthParams;
import net.zriot.ebike.pojo.request.ebike.JoinMembershipParams;
import net.zriot.ebike.pojo.request.ebike.RenewParams;
import net.zriot.ebike.pojo.request.order.Money;
import net.zriot.ebike.pojo.response.MessageDto;
import net.zriot.ebike.service.ebike.EBikeService;
import net.zriot.ebike.service.order.OrderService;
import net.zriot.ebike.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Autowired
    OrderService orderService;

    @PostMapping("/list")
    @AuthRequire(Auth.LOGIN)
    public MessageDto list(AuthParams authParams) {
        List<EBike> ebikes =  eBikeService.findAllByUid(authParams.getUid());
        Map<String, Object> data = new HashMap<>();
        data.put("ebikes", ebikes);
        return MessageDto.responseSuccess(data);
    }

    @PostMapping("/join")
    @AuthRequire(Auth.LOGIN)
    public MessageDto join(JoinMembershipParams params, AuthParams authParams) throws GException {
        // check ebike
        EBike ebike = eBikeService.findOneBySn(params.getEbikeSn());
        if (ebike == null) {
            throw new GException(ErrorConstants.NOT_EXIST_EBIKE);
        }
        if (ebike.getIsMembership() == 1) {
            throw new GException(ErrorConstants.ALREADY_MEMBERSHIP);
        }

        // check user money
        User user = userService.getUserByUid(authParams.getUid());
        BigDecimal money = user.getMoney();

        BigDecimal membership = params.getMembership();
        BigDecimal monthFee = params.getMonthFee();
        BigDecimal fee = membership.add(monthFee);
        String currency = params.getCurrency() == null ? Constants.CURRENCY : params.getCurrency();

        if (money.compareTo(fee) == -1) {
            throw new GException(ErrorConstants.LACK_MONEY);
        }

        ebike = eBikeService.joinMembership(ebike, params);
        OrderMembership orderMembership = orderService.joinMembership(ebike, new Money(membership, currency));
        OrderMonthPay orderMonthPay = orderService.renewMonthly(ebike, new Money(monthFee, currency));
        user = userService.minusMoney(user, fee);

        Map<String, Object> data = new HashMap<>();
        data.put("balance", user.getMoney());
        data.put("orderSn", orderMembership.getSn());
        data.put("orderTime", orderMembership.getCreateTime());
        data.put("monthStartDate", ebike.getMonthStartDate());
        data.put("monthEndDate", ebike.getMonthEndDate());
        return MessageDto.responseSuccess(data);
    }

    @PostMapping("/renew")
    @AuthRequire(Auth.LOGIN)
    public MessageDto renew(RenewParams params, AuthParams authParams) throws GException {
        // check ebike
        EBike ebike = eBikeService.findOneBySn(params.getEbikeSn());
        if (ebike == null) {
            throw new GException(ErrorConstants.NOT_EXIST_EBIKE);
        }
        if (ebike.getIsMembership() == 0) {
            throw new GException(ErrorConstants.NO_MEMBERSHIP);
        }
        if (ebike.getMonthEndDate().isAfter(LocalDate.now())) {
            throw new GException(ErrorConstants.ALREADY_RENEW);
        }

        // check user money
        BigDecimal monthFee = params.getMonthFee();
        String currency = params.getCurrency() == null ? Constants.CURRENCY : params.getCurrency();
        User user = userService.getUserByUid(authParams.getUid());
        BigDecimal money = user.getMoney();
        if (money.compareTo(monthFee) == -1) {
            throw new GException(ErrorConstants.LACK_MONEY);
        }

        ebike = eBikeService.renew(params);
        OrderMonthPay orderMonthPay = orderService.renewMonthly(ebike, new Money(monthFee, currency));
        user = userService.minusMoney(user, monthFee);

        Map<String, Object> data = new HashMap<>();
        data.put("balance", user.getMoney());
        data.put("orderSn", orderMonthPay.getSn());
        data.put("orderTime", orderMonthPay.getCreateTime());
        data.put("monthStartDate", ebike.getMonthStartDate());
        data.put("monthEndDate", ebike.getMonthEndDate());
        return MessageDto.responseSuccess(data);
    }

}
