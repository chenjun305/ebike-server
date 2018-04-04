package com.ecgobike.service;

import com.ecgobike.common.enums.ProductType;
import com.ecgobike.entity.Product;

import java.util.List;

/**
 * Created by ChenJun on 2018/4/4.
 */
public interface ProductService {
    List<Product> findAllProducts();
    List<Product> findByType(ProductType type);
    Product getOne(Long id);
}
