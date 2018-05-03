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
    List<Shop> findAll();
    Shop create(Shop shop);
    Shop getShopById(Long id);

    List<Shop> saveAll(List<Shop> shopList);
}
