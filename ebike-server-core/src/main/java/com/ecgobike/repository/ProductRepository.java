package com.ecgobike.repository;

import com.ecgobike.common.enums.ProductType;
import com.ecgobike.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ChenJun on 2018/3/28.
 */
@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByType(ProductType type);
}
