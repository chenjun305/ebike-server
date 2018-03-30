package com.ecgobike.service;

import com.ecgobike.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by ChenJun on 2018/3/11.
 */
public interface ShopService {
    List<Shop> near(Double latitude, Double longitude);
    Page<Shop> findAll(Pageable pageable);
    Shop create(Shop shop);
}