package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.enums.ProductType;
import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.Product;
import com.ecgobike.entity.PurchaseOrder;
import com.ecgobike.entity.Shop;
import com.ecgobike.entity.Staff;
import com.ecgobike.pojo.request.AuthParams;
import com.ecgobike.pojo.request.PurchaseParams;
import com.ecgobike.pojo.response.BatteryProductVO;
import com.ecgobike.pojo.response.EBikeProductVO;
import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.pojo.response.PurchaseOrderVO;
import com.ecgobike.service.*;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ChenJun on 2018/3/23.
 */
@RestController
@RequestMapping("/product")
public class ShopProductController {

    @Autowired
    ProductService productService;

    @Autowired
    StaffService staffService;

    @Autowired
    PurchaseOrderService purchaseOrderService;

    @Autowired
    PaymentOrderService paymentOrderService;

    @Autowired
    LogisticsService logisticsService;

    @Autowired
    BatteryService batteryService;

    @Autowired
    Mapper mapper;

    @RequestMapping("/list")
    @AuthRequire(Auth.STAFF)
    public AppResponse list(AuthParams params){
        Shop shop = staffService.getShopByUid(params.getUid());
        long shopId = shop.getId();
        Map<String, Object> data = new HashMap<>();
        List<EBikeProductVO> ebikeProducts = new ArrayList<>();
        List<BatteryProductVO> batteryProducts = new ArrayList<>();
        List<Product> productList = productService.findAllProducts();
        for (Product product : productList) {
            if (product.getType() == ProductType.EBIKE) {
                EBikeProductVO eBikeProductVO = mapper.map(product, EBikeProductVO.class);
                long sellNum = paymentOrderService.countProductSellOrdersInShop(product, shopId);
                long stockNum = logisticsService.countProductStockInShop(product, shop);
                eBikeProductVO.setSellNum(sellNum);
                eBikeProductVO.setStockNum(stockNum);
                ebikeProducts.add(eBikeProductVO);
            } else if (product.getType() == ProductType.BATTERY) {
                BatteryProductVO batteryProductVO = mapper.map(product, BatteryProductVO.class);
                long stockNum = batteryService.countProductStockInShop(product, shopId);
                batteryProductVO.setStockNum(stockNum);
                batteryProducts.add(batteryProductVO);
            }

        }
        data.put("ebikeProducts", ebikeProducts);
        data.put("batteryProducts", batteryProducts);
        return AppResponse.responseSuccess(data);
    }

    @RequestMapping("/purchase")
    @AuthRequire(Auth.STAFF)
    public AppResponse purchase(PurchaseParams params) throws GException {
        Staff staff = staffService.findOneByUid(params.getUid());
        Product product = productService.getOne(params.getProductId());
        if (product == null) {
            throw new GException(ErrorConstants.NOT_EXIST_PRODUCT);
        }
        PurchaseOrder purchaseOrder = purchaseOrderService.purchase(staff, product, params.getRequireNum());
        PurchaseOrderVO purchaseOrderVO = mapper.map(purchaseOrder, PurchaseOrderVO.class);
        Map<String, Object> data = new HashMap<>();
        data.put("purchaseOrder", purchaseOrderVO);
        return AppResponse.responseSuccess(data);
    }
}
