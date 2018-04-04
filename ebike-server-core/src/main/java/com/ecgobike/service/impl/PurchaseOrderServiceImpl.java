package com.ecgobike.service.impl;

import com.ecgobike.common.enums.PurchaseOrderStatus;
import com.ecgobike.common.util.IdGen;
import com.ecgobike.entity.Product;
import com.ecgobike.entity.PurchaseOrder;
import com.ecgobike.entity.Staff;
import com.ecgobike.repository.PurchaseOrderRepository;
import com.ecgobike.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by ChenJun on 2018/4/4.
 */
@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Override
    public PurchaseOrder purchase(Staff staff, Long productId, Integer requireNum) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setSn(IdGen.genOrderSn());
        purchaseOrder.setProductId(productId);
        purchaseOrder.setRequireNum(requireNum);
        purchaseOrder.setStaffUid(staff.getUid());
        purchaseOrder.setShopId(staff.getShopId());
        purchaseOrder.setPurchaseTime(new Date());
        purchaseOrder.setStatus(PurchaseOrderStatus.REQUIRE);
        purchaseOrder.setCreateTime(LocalDateTime.now());
        purchaseOrder.setUpdateTime(LocalDateTime.now());
        return purchaseOrderRepository.save(purchaseOrder);
    }
}
