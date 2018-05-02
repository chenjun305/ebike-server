package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.enums.*;
import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.*;
import com.ecgobike.helper.FileUrlHelper;
import com.ecgobike.pojo.request.*;
import com.ecgobike.pojo.response.*;
import com.ecgobike.service.*;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
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
        if (logistics.getProduct().getType() != ProductType.EBIKE) {
            throw new GException(ErrorConstants.NOT_EBIKE_PRODUCT);
        }
        Map<String, Object> data = new HashMap<>();

        EBike eBike = eBikeService.findOneBySn(ebikeSn);
        if (eBike != null) {
            EBikeInfoVO eBikeInfoVO = mapper.map(eBike, EBikeInfoVO.class);

            if (eBike.getExpireDate().isBefore(LocalDate.now())) {
                eBikeInfoVO.setIsExpire(true);
            } else {
                eBikeInfoVO.setIsExpire(false);
            }
            eBikeInfoVO.setLogisticsStatus(logistics.getStatus());
            data.put("ebike", eBikeInfoVO);
        } else {
            EBikeInfoVO eBikeInfoVO = new EBikeInfoVO();
            eBikeInfoVO.setSn(logistics.getSn());
            eBikeInfoVO.setLogisticsStatus(logistics.getStatus());
            eBikeInfoVO.setProduct(mapper.map(logistics.getProduct(), ProductVO.class));
            data.put("ebike", eBikeInfoVO);
        }
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
        Staff staff = staffService.findOneByUid(params.getUid());

        EBike eBike = eBikeService.findOneBySn(params.getEbikeSn());
        if (eBike != null && eBike.getUid() != null) {
            throw  new GException(ErrorConstants.ALREADY_SELLED);
        }

        Logistics logistics = logisticsService.sell(params.getEbikeSn(), staff.getShop().getId());

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
        PaymentOrder paymentOrder = paymentOrderService.createSellOrder(staff, user, eBike);
        PaymentOrderVO paymentOrderVO = mapper.map(paymentOrder, PaymentOrderVO.class);

        Map<String, Object> data = new HashMap<>();
        data.put("paymentOrder", paymentOrderVO);
        return AppResponse.responseSuccess(data);
    }

    @PostMapping("/join")
    @AuthRequire(Auth.STAFF)
    public AppResponse join(JoinParams params) throws GException {
        Staff staff = staffService.findOneByUid(params.getUid());
        EBike eBike = eBikeService.joinMembership(params.getEbikeSn());
        PaymentOrder order = paymentOrderService.createMembershipOrder(OrderType.STAFF_JOIN_MEMBERSHIP, eBike, staff, null);

        EBikeVO eBikeVO = mapper.map(eBike, EBikeVO.class);
        PaymentOrderVO paymentOrderVO = mapper.map(order, PaymentOrderVO.class);
        Map<String, Object> data = new HashMap<>();
        data.put("ebike", eBikeVO);
        data.put("order", paymentOrderVO);
        return AppResponse.responseSuccess(data);
    }

    @PostMapping("/renew")
    @AuthRequire(Auth.STAFF)
    public AppResponse renew(RenewParams params) throws GException {
        Staff staff = staffService.findOneByUid(params.getUid());
        EBike eBike = eBikeService.renew(params.getEbikeSn(), params.getMonthNum());
        PaymentOrder order = paymentOrderService.createMembershipOrder(OrderType.STAFF_RENEW_MONTHLY, eBike, staff, params.getMonthNum());

        EBikeVO eBikeVO = mapper.map(eBike, EBikeVO.class);
        PaymentOrderVO paymentOrderVO = mapper.map(order, PaymentOrderVO.class);
        Map<String, Object> data = new HashMap<>();
        data.put("ebike", eBikeVO);
        data.put("order", paymentOrderVO);
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
        Long shopId = staffService.findOneByUid(params.getUid()).getShop().getId();
        Page<PaymentOrder> sellList = paymentOrderService.findProductSellOrdersInShop(product, shopId, pageable);
        Page<PaymentOrderVO> voList = sellList.map(paymentOrder -> mapper.map(paymentOrder, PaymentOrderVO.class));
        Map<String, Object> data = new HashMap<>();
        data.put("sellList", voList);
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
        Shop shop = staffService.findOneByUid(params.getUid()).getShop();
        Page<Logistics> stockList = logisticsService.findProductStockInShop(product, shop, pageable);
        Page<LogisticsVO> voList = stockList.map(logistics -> mapper.map(logistics, LogisticsVO.class));
        Map<String, Object> data = new HashMap<>();
        data.put("stockList", voList);
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
