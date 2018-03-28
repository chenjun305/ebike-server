package net.zriot.ebike.controller;

import net.zriot.ebike.common.constant.ErrorConstants;
import net.zriot.ebike.common.exception.GException;
import net.zriot.ebike.common.util.IdGen;
import net.zriot.ebike.entity.OrderSellEBike;
import net.zriot.ebike.entity.EBike;
import net.zriot.ebike.entity.Staff;
import net.zriot.ebike.entity.User;
import net.zriot.ebike.pojo.request.AuthParams;
import net.zriot.ebike.pojo.request.SellBikeParams;
import net.zriot.ebike.pojo.response.MessageDto;
import net.zriot.ebike.service.OrderSellEBikeService;
import net.zriot.ebike.service.EBikeService;
import net.zriot.ebike.service.StaffService;
import net.zriot.ebike.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChenJun on 2018/3/26.
 */
@RestController
@RequestMapping("/ebike")
public class EBikeController {

    @Autowired
    EBikeService eBikeService;

    @Autowired
    UserService userService;

    @Autowired
    StaffService staffService;

    @Autowired
    OrderSellEBikeService orderSellEBikeService;

    @RequestMapping("/info")
    public MessageDto info(String ebikeSn) throws GException {
        EBike eBike = eBikeService.findOneBySn(ebikeSn);
        if (eBike == null) {
            throw new GException(ErrorConstants.NOT_EXIST_EBIKE);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("ebike", eBike);
        return MessageDto.responseSuccess(data);
    }

    @PostMapping("/sell")
    public MessageDto sell(SellBikeParams params, AuthParams authParams) throws GException {
        EBike eBike = eBikeService.findOneBySn(params.getEbikeSn());
        if (eBike == null) {
            throw new GException(ErrorConstants.NOT_EXIST_EBIKE);
        }
        if (eBike.getUid() != null) {
            throw  new GException(ErrorConstants.ALREADY_SELLED);
        }
        Staff staff = staffService.findOneByUid(authParams.getUid());
        if (staff == null) {
            throw new GException(ErrorConstants.NOT_EXIST_STAFF);
        }
        User user = userService.getUserByTel(params.getPhoneNum());
        if (user == null) {
            // new user
            user = new User();
            user.setUid(IdGen.uuid());
            user.setTel(params.getPhoneNum());
        }
        user.setIsReal((byte)1);
        user.setRealName(params.getRealName());
        user.setAddress(params.getAddress());
        user = userService.update(user);

        OrderSellEBike order = new OrderSellEBike();
        order.setSn(IdGen.genOrderSn());
        order.setEbikeSn(eBike.getSn());
        order.setUid(user.getUid());
        order.setStaffUid(authParams.getUid());
        order.setShopId(staff.getShopId());
        order.setPrice(new BigDecimal(100));
        order.setCurrency("USD");
        order.setStatus((byte)1);
        orderSellEBikeService.save(order);

        eBike.setUid(user.getUid());
        eBikeService.save(eBike);


        Map<String, Object> data = new HashMap<>();
        data.put("order", order);
        return MessageDto.responseSuccess(data);
    }
}
