package com.ecgobike.service;

import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.Product;
import com.ecgobike.entity.PurchaseOrder;
import com.ecgobike.entity.Shop;
import com.ecgobike.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by ChenJun on 2018/4/4.
 */
public interface PurchaseOrderService {
    PurchaseOrder findOneBySn(String sn);
    PurchaseOrder purchase(Staff staff, Product product, Integer requireNum);
    PurchaseOrder permit(String sn, Integer permitNum, String permitUid) throws GException;
    PurchaseOrder departure(PurchaseOrder purchaseOrder, String departureUid);
    PurchaseOrder takeOver(String sn, Staff staff) throws GException;
    Page<PurchaseOrder> findAllByShop(Shop shop, Pageable pageable);
    Page<PurchaseOrder> findAllRequire(Pageable pageable);
    Page<PurchaseOrder> findAllPermit(Pageable pageable);
}
