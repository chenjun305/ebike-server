package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.enums.*;
import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.*;
import com.ecgobike.helper.FileUrlHelper;
import com.ecgobike.pojo.request.*;
import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.pojo.response.EBikeInfoVO;
import com.ecgobike.service.*;
import com.google.common.base.Strings;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
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
    ShopStaffService shopStaffService;

    @Autowired
    PaymentOrderService paymentOrderService;

    @Autowired
    LogisticsService logisticsService;

    @Autowired
    ProductService productService;

    @Autowired
    FileService fileService;

    @Autowired
    Mapper mapper;

    @RequestMapping("/info")
    @AuthRequire(Auth.STAFF)
    public AppResponse info(String ebikeSn) throws GException {
        System.out.println("in Method ebike/info");
        Logistics logistics = logisticsService.findOneBySn(ebikeSn);
        if (logistics == null) {
            throw new GException(ErrorConstants.NOT_EXIST_PRODUCT);
        }
        EBike eBike = eBikeService.findOneBySn(ebikeSn);
        EBikeInfoVO eBikeInfoVO = mapper.map(eBike, EBikeInfoVO.class);
        Map<String, Object> data = new HashMap<>();
        data.put("logistics", logistics);
        data.put("ebike", eBikeInfoVO);
        return AppResponse.responseSuccess(data);
    }

    @PostMapping("/sell")
    @AuthRequire(Auth.STAFF)
    public AppResponse sell(
            @RequestParam(value = "idCardFile1") MultipartFile file1,
            @RequestParam(value = "idCardFile2") MultipartFile file2,
            @RequestParam(value = "idCardFile3") MultipartFile file3,
            SellBikeParams params
    ) throws GException {
        ShopStaff shopStaff = shopStaffService.findOneByUid(params.getUid());
        Logistics logistics = logisticsService.sell(params.getEbikeSn(), shopStaff.getShopId());

        EBike eBike = eBikeService.findOneBySn(params.getEbikeSn());
        if (eBike != null && eBike.getUid() != null) {
            throw  new GException(ErrorConstants.ALREADY_SELLED);
        }

        User user = userService.getOrCreate(params.getPhoneNum());
        user.setIsReal((byte)1);
        user.setRealName(params.getRealName());
        user.setAddress(params.getAddress());
        user.setIdCardNum(params.getIdCardNum());
        MultipartFile[] files = {file1, file2, file3};
        String[] fileNames = fileService.saveFile(FileType.USER_IDCARD, user.getUid(), files);
        String idCardPics = null;
        for (String fileName : fileNames) {
            if (idCardPics == null) {
                idCardPics = fileName;
            } else {
                idCardPics = idCardPics + "," + fileName;
            }
        }
        System.out.println("upload " + idCardPics + "success!");
        user.setIdCardPics(idCardPics);

        user = userService.update(user);

        eBike = eBikeService.sell(user, params.getEbikeSn(), logistics.getProduct());
        PaymentOrder paymentOrder = paymentOrderService.createSellOrder(shopStaff, user, eBike);

        Map<String, Object> data = new HashMap<>();
        data.put("paymentOrder", paymentOrder);
        return AppResponse.responseSuccess(data);
    }

    @PostMapping("/join")
    @AuthRequire(Auth.STAFF)
    public AppResponse join(JoinParams params) throws GException {
        ShopStaff shopStaff = shopStaffService.findOneByUid(params.getUid());
        EBike eBike = eBikeService.joinMembership(params.getEbikeSn());
        PaymentOrder order = paymentOrderService.createMembershipOrder(OrderType.STAFF_JOIN_MEMBERSHIP, eBike, shopStaff, null);

        Map<String, Object> data = new HashMap<>();
        data.put("ebike", eBike);
        data.put("order", order);
        return AppResponse.responseSuccess(data);
    }

    @PostMapping("/renew")
    @AuthRequire(Auth.STAFF)
    public AppResponse renew(RenewParams params) throws GException {
        ShopStaff shopStaff = shopStaffService.findOneByUid(params.getUid());
        EBike eBike = eBikeService.renew(params.getEbikeSn(), params.getMonthNum());
        PaymentOrder order = paymentOrderService.createMembershipOrder(OrderType.STAFF_RENEW_MONTHLY, eBike, shopStaff, params.getMonthNum());

        Map<String, Object> data = new HashMap<>();
        data.put("ebike", eBike);
        data.put("order", order);
        return AppResponse.responseSuccess(data);
    }

    @RequestMapping("/sell/list")
    @AuthRequire(Auth.STAFF)
    public AppResponse sellList(
            ProductParams params,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) throws GException {
        Product product = productService.getOne(params.getProductId());
        if (product == null) {
            throw new GException(ErrorConstants.NOT_EXIST_PRODUCT);
        }
        if (product.getType() != ProductType.EBIKE) {
            throw new GException(ErrorConstants.NOT_EBIKE_PRODUCT);
        }
        Long shopId = shopStaffService.findOneByUid(params.getUid()).getShopId();
        Page<PaymentOrder> sellList = paymentOrderService.findProductSellOrdersInShop(product, shopId, pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("sellList", sellList);
        return AppResponse.responseSuccess(data);
    }

    @RequestMapping("/stock/list")
    @AuthRequire(Auth.STAFF)
    public AppResponse stockList(
            ProductParams params,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) throws GException {
        Product product = productService.getOne(params.getProductId());
        if (product == null) {
            throw new GException(ErrorConstants.NOT_EXIST_PRODUCT);
        }
        if (product.getType() != ProductType.EBIKE) {
            throw new GException(ErrorConstants.NOT_EBIKE_PRODUCT);
        }
        Long shopId = shopStaffService.findOneByUid(params.getUid()).getShopId();
        Page<Logistics> stockList = logisticsService.findProductStockInShop(product, shopId, pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("stockList", stockList);
        return AppResponse.responseSuccess(data);
    }

    @RequestMapping("/customer")
    @AuthRequire(Auth.STAFF)
    public AppResponse customer(CustomerParams params) {
        User customer = userService.getUserByUid(params.getCustomerUid());
        Map<String, Object> data = new HashMap<>();
        data.put("customer", FileUrlHelper.dealUser(customer));
        return AppResponse.responseSuccess(data);
    }

}
