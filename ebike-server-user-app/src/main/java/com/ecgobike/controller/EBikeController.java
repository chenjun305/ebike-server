package com.ecgobike.controller;

import com.ecgobike.common.constant.Constants;
import com.ecgobike.common.constant.MonthNumFee;
import com.ecgobike.common.enums.OrderType;
import com.ecgobike.pojo.request.AuthParams;
import com.ecgobike.pojo.request.JoinParams;
import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.exception.GException;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.entity.EBike;
import com.ecgobike.entity.PaymentOrder;
import com.ecgobike.entity.User;
import com.ecgobike.pojo.request.RenewParams;
import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.pojo.response.EBikeInfoVO;
import com.ecgobike.pojo.response.PaymentOrderVO;
import com.ecgobike.pojo.response.ProductVO;
import com.ecgobike.service.EBikeService;
import com.ecgobike.service.PaymentOrderService;
import com.ecgobike.service.UserService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ebike")
public class EBikeController {

    @Autowired
    EBikeService eBikeService;

    @Autowired
    UserService userService;

    @Autowired
    PaymentOrderService paymentOrderService;

    @Autowired
    Mapper mapper;

    @PostMapping("/list")
    @AuthRequire(Auth.USER)
    public AppResponse list(AuthParams authParams) {
        List<EBike> ebikes =  eBikeService.findAllByUid(authParams.getUid());
        List<EBikeInfoVO> voList = ebikes.stream().map(ebike -> {
            EBikeInfoVO eBikeInfoVO = mapper.map(ebike, EBikeInfoVO.class);
            ProductVO productVO = mapper.map(ebike.getProduct(), ProductVO.class);
            eBikeInfoVO.setProduct(productVO);
            return eBikeInfoVO;
        }).collect(Collectors.toList());
        Map<String, Object> data = new HashMap<>();
        data.put("ebikes", voList);
        return AppResponse.responseSuccess(data);
    }

    @PostMapping("/join")
    @AuthRequire(Auth.USER)
    public AppResponse join(JoinParams params) throws GException {
        // check user money
        User user = userService.getUserByUid(params.getUid());
        BigDecimal money = user.getMoney();

        if (money.compareTo(Constants.MEMBERSHIP_FEE) == -1) {
            throw new GException(ErrorConstants.LACK_MONEY);
        }

        EBike eBike = eBikeService.joinMembership(params.getEbikeSn());
        user = userService.minusMoney(user, Constants.MEMBERSHIP_FEE);
        PaymentOrder order = paymentOrderService.createMembershipOrder(OrderType.USER_JOIN_MEMBERSHIP, eBike, null, null);
        PaymentOrderVO paymentOrderVO = mapper.map(order, PaymentOrderVO.class);
        Map<String, Object> data = new HashMap<>();
        data.put("balance", user.getMoney());
        //data.put("ebike", eBike);
        data.put("order", paymentOrderVO);
        return AppResponse.responseSuccess(data);
    }

    @PostMapping("/renew")
    @AuthRequire(Auth.USER)
    public AppResponse renew(RenewParams params) throws GException {
        // check user money
        User user = userService.getUserByUid(params.getUid());
        BigDecimal money = user.getMoney();
        BigDecimal monthFee = MonthNumFee.getFee(params.getMonthNum());
        if (monthFee == null) {
            throw new GException(ErrorConstants.NOT_EXIST_MONTH_NUM_FEE_RULE);
        }
        if (money.compareTo(monthFee) == -1) {
            throw new GException(ErrorConstants.LACK_MONEY);
        }

        EBike eBike = eBikeService.renew(params.getEbikeSn(), params.getMonthNum());
        user = userService.minusMoney(user, monthFee);
        PaymentOrder order = paymentOrderService.createMembershipOrder(OrderType.USER_RENEW_MONTHLY, eBike, null, params.getMonthNum());
        PaymentOrderVO paymentOrderVO = mapper.map(order, PaymentOrderVO.class);
        Map<String, Object> data = new HashMap<>();
        data.put("balance", user.getMoney());
        //data.put("ebike", eBike);
        data.put("order", paymentOrderVO);
        return AppResponse.responseSuccess(data);
    }

}
