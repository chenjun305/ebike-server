package com.ecgobike.service.impl;

import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.enums.PurchaseOrderStatus;
import com.ecgobike.common.exception.GException;
import com.ecgobike.common.util.IdGen;
import com.ecgobike.entity.Product;
import com.ecgobike.entity.PurchaseOrder;
import com.ecgobike.entity.Shop;
import com.ecgobike.entity.Staff;
import com.ecgobike.repository.PurchaseOrderRepository;
import com.ecgobike.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/4/4.
 */
@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Override
    public PurchaseOrder purchase(Staff staff, Product product, Integer requireNum) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setSn(IdGen.genOrderSn());
        purchaseOrder.setProduct(product);
        purchaseOrder.setRequireNum(requireNum);
        purchaseOrder.setStaffUid(staff.getUid());
        purchaseOrder.setShop(staff.getShop());
        purchaseOrder.setPurchaseTime(LocalDateTime.now());
        purchaseOrder.setStatus(PurchaseOrderStatus.REQUIRE);
        purchaseOrder.setCreateTime(LocalDateTime.now());
        purchaseOrder.setUpdateTime(LocalDateTime.now());
        return purchaseOrderRepository.save(purchaseOrder);
    }

    @Override
    public Page<PurchaseOrder> findAllByShop(Shop shop, Pageable pageable) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setShop(shop);
        Example<PurchaseOrder> example = Example.of(purchaseOrder);
        return purchaseOrderRepository.findAll(example, pageable);
    }

    @Override
    public Page<PurchaseOrder> findAllRequire(Pageable pageable) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setStatus(PurchaseOrderStatus.REQUIRE);
        Example<PurchaseOrder> example = Example.of(purchaseOrder);
        return purchaseOrderRepository.findAll(example, pageable);
    }

    @Override
    public Page<PurchaseOrder> findAllPermit(Pageable pageable) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setStatus(PurchaseOrderStatus.PERMIT);
        Example<PurchaseOrder> example = Example.of(purchaseOrder);
        return purchaseOrderRepository.findAll(example, pageable);
    }

    @Override
    public PurchaseOrder permit(String sn, Integer permitNum, String permitUid) throws GException {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findOneBySn(sn);
        if (purchaseOrder == null) {
            throw new GException(ErrorConstants.NOT_EXIST_PURCHASE_ORDER);
        }
        purchaseOrder.setPermitNum(permitNum);
        purchaseOrder.setPermitUid(permitUid);
        purchaseOrder.setPermitTime(LocalDateTime.now());
        purchaseOrder.setStatus(PurchaseOrderStatus.PERMIT);
        purchaseOrder.setUpdateTime(LocalDateTime.now());
        return purchaseOrderRepository.save(purchaseOrder);
    }

    @Override
    public PurchaseOrder departure(PurchaseOrder purchaseOrder, String departureUid) {
        purchaseOrder.setDepartureUid(departureUid);
        purchaseOrder.setDepartureTime(LocalDateTime.now());
        purchaseOrder.setStatus(PurchaseOrderStatus.TRANSIT);
        purchaseOrder.setUpdateTime(LocalDateTime.now());
        return purchaseOrderRepository.save(purchaseOrder);
    }

    @Override
    public PurchaseOrder takeOver(String sn, Staff staff) throws GException {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findOneBySn(sn);
        if (purchaseOrder == null) {
            throw new GException(ErrorConstants.NOT_EXIST_PURCHASE_ORDER);
        }
        if (staff.getShop().getId() != purchaseOrder.getShop().getId()) {
            throw new GException(ErrorConstants.NOT_YOUR_SHOP_PURCHASE);
        }
        purchaseOrder.setTakeOverUid(staff.getUid());
        purchaseOrder.setTakeOverTime(LocalDateTime.now());
        purchaseOrder.setStatus(PurchaseOrderStatus.TAKE_OVER);
        purchaseOrder.setUpdateTime(LocalDateTime.now());
        return purchaseOrderRepository.save(purchaseOrder);
    }

    @Override
    public PurchaseOrder findOneBySn(String sn) {
        return purchaseOrderRepository.findOneBySn(sn);
    }


}
