package com.ecgobike.service;

import com.ecgobike.entity.Product;
import com.ecgobike.entity.PurchaseOrder;
import com.ecgobike.entity.Staff;

/**
 * Created by ChenJun on 2018/4/4.
 */
public interface PurchaseOrderService {
    PurchaseOrder purchase(Staff staff, Long productId, Integer requireNum);
}
