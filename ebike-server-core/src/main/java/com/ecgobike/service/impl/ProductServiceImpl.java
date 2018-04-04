package com.ecgobike.service.impl;

import com.ecgobike.common.enums.ProductType;
import com.ecgobike.entity.Product;
import com.ecgobike.repository.ProductRepository;
import com.ecgobike.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ChenJun on 2018/4/4.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;


    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findByType(ProductType type) {
        return productRepository.findByType(type);
    }

}
