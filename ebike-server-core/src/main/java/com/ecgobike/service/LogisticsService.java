package com.ecgobike.service;

import com.ecgobike.common.enums.LogisticsStatus;
import com.ecgobike.common.enums.ProductType;
import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.Logistics;
import com.ecgobike.entity.Product;
import com.ecgobike.entity.PurchaseOrder;
import com.ecgobike.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by ChenJun on 2018/4/10.
 */
public interface LogisticsService {
    Logistics findOneBySn(String sn);
    Page<Logistics> findAll(Pageable pageable);
    Page<Logistics> findAllByType(ProductType type, Pageable pageable);
    Page<Logistics> findAllByStatus(LogisticsStatus status, Pageable pageable);
    Page<Logistics> findProductStockInShop(Product product, Long shopId, Pageable pageable);
    long countProductStockInShop(Product product, Long shopId);
    List<Logistics> in(Product product, List<String> snList) throws GException;
    List<Logistics> out(PurchaseOrder purchaseOrder, List<String> snList) throws GException;
    List<Logistics> shopIn(PurchaseOrder purchaseOrder);
    Logistics sell(String sn, Staff staff) throws GException;
}
