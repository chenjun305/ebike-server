package com.ecgobike.service;

import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.Product;
import com.ecgobike.entity.PurchaseOrder;
import com.ecgobike.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by ChenJun on 2018/4/4.
 */
public interface PurchaseOrderService {
    PurchaseOrder purchase(Staff staff, Long productId, Integer requireNum);
    Page<PurchaseOrder> findAllRequire(Pageable pageable);
    Page<PurchaseOrder> findAllPermit(Pageable pageable);
    PurchaseOrder permit(String sn, Integer permitNum) throws GException;
    PurchaseOrder departure(PurchaseOrder purchaseOrder);
    PurchaseOrder findOneBySn(String sn);
}
