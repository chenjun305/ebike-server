package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.constant.Constants;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.enums.*;
import com.ecgobike.common.exception.GException;
import com.ecgobike.common.util.IdGen;
import com.ecgobike.entity.*;
import com.ecgobike.pojo.request.*;
import com.ecgobike.pojo.response.MessageDto;
import com.ecgobike.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.soap.SOAPBinding;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChenJun on 2018/3/26.
 */
@RestController
@RequestMapping("/ebike")
public class ShopEBikeController {

    @Autowired
    EBikeService eBikeService;

    @Autowired
    UserService userService;

    @Autowired
    StaffService staffService;

    @Autowired
    PaymentOrderService paymentOrderService;

    @Autowired
    LogisticsService logisticsService;

    @Autowired
    ProductService productService;

    @RequestMapping("/info")
    @AuthRequire(Auth.STAFF)
    public MessageDto info(String ebikeSn) throws GException {
        Logistics logistics = logisticsService.findOneBySn(ebikeSn);
        if (logistics == null) {
            throw new GException(ErrorConstants.NOT_EXIST_PRODUCT);
        }
        EBike eBike = eBikeService.findOneBySn(ebikeSn);
        Map<String, Object> data = new HashMap<>();
        data.put("logistics", logistics);
        data.put("ebike", eBike);
        return MessageDto.responseSuccess(data);
    }

    @PostMapping("/sell")
    @AuthRequire(Auth.STAFF)
    public MessageDto sell(SellBikeParams params) throws GException {
        Staff staff = staffService.findOneByUid(params.getUid());

        Logistics logistics = logisticsService.sell(params.getEbikeSn(), staff);

        EBike eBike = eBikeService.findOneBySn(params.getEbikeSn());
        if (eBike != null && eBike.getUid() != null) {
            throw  new GException(ErrorConstants.ALREADY_SELLED);
        }
        User user = userService.getOrCreate(params.getPhoneNum());
        user.setIsReal((byte)1);
        user.setRealName(params.getRealName());
        user.setAddress(params.getAddress());
        user = userService.update(user);

        eBike = eBikeService.sell(user, params.getEbikeSn(), logistics.getProduct());
        PaymentOrder paymentOrder = paymentOrderService.createSellOrder(staff, user, eBike);

        Map<String, Object> data = new HashMap<>();
        data.put("paymentOrder", paymentOrder);
        return MessageDto.responseSuccess(data);
    }

    @PostMapping("/join")
    @AuthRequire(Auth.STAFF)
    public MessageDto join(JoinParams params) throws GException {
        Staff staff = staffService.findOneByUid(params.getUid());
        EBike eBike = eBikeService.joinMembership(params.getEbikeSn());
        PaymentOrder order = paymentOrderService.createMembershipOrder(OrderType.STAFF_JOIN_MEMBERSHIP, eBike, staff, null);

        Map<String, Object> data = new HashMap<>();
        data.put("ebike", eBike);
        data.put("order", order);
        return MessageDto.responseSuccess(data);
    }

    @PostMapping("/renew")
    @AuthRequire(Auth.STAFF)
    public MessageDto renew(RenewParams params) throws GException {
        Staff staff = staffService.findOneByUid(params.getUid());
        EBike eBike = eBikeService.renew(params.getEbikeSn(), params.getMonthNum());
        PaymentOrder order = paymentOrderService.createMembershipOrder(OrderType.STAFF_RENEW_MONTHLY, eBike, staff, params.getMonthNum());

        Map<String, Object> data = new HashMap<>();
        data.put("ebike", eBike);
        data.put("order", order);
        return MessageDto.responseSuccess(data);
    }

    @RequestMapping("/sell/list")
    @AuthRequire(Auth.STAFF)
    public MessageDto sellList(
            ProductParams params,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) throws GException {
        Staff staff = staffService.findOneByUid(params.getUid());
        Product product = productService.getOne(params.getProductId());
        if (product == null) {
            throw new GException(ErrorConstants.NOT_EXIST_PRODUCT);
        }
        if (product.getType() != ProductType.EBIKE) {
            throw new GException(ErrorConstants.NOT_EBIKE_PRODUCT);
        }
        Page<PaymentOrder> sellList = paymentOrderService.findProductSellOrdersInShop(product, staff.getShopId(), pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("sellList", sellList);
        return MessageDto.responseSuccess(data);
    }

    @RequestMapping("/stock/list")
    @AuthRequire(Auth.STAFF)
    public MessageDto stockList(
            ProductParams params,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) throws GException {
        Staff staff = staffService.findOneByUid(params.getUid());
        Product product = productService.getOne(params.getProductId());
        if (product == null) {
            throw new GException(ErrorConstants.NOT_EXIST_PRODUCT);
        }
        if (product.getType() != ProductType.EBIKE) {
            throw new GException(ErrorConstants.NOT_EBIKE_PRODUCT);
        }
        Page<Logistics> stockList = logisticsService.findProductStockInShop(product, staff.getShopId(), pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("stockList", stockList);
        return MessageDto.responseSuccess(data);
    }

    @RequestMapping("/customer")
    @AuthRequire(Auth.STAFF)
    public MessageDto customer(CustomerParams params) {
        User customer = userService.getUserByUid(params.getCustomerUid());
        Map<String, Object> data = new HashMap<>();
        data.put("customer", customer);
        return MessageDto.responseSuccess(data);
    }

}
