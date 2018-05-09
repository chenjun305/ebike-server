package com.ecgobike.service.impl;

import com.ecgobike.entity.Shop;
import com.ecgobike.repository.ShopRepository;
import com.ecgobike.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by ChenJun on 2018/3/11.
 */
@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    ShopRepository shopRepository;

    @Override
    public List<Shop> near(Double latitude, Double longitude) {
        return shopRepository.findAll();
    }

    @Override
    public Page<Shop> findAll(Pageable pageable) {
        return shopRepository.findAll(pageable);
    }

    @Override
    public List<Shop> findAll() {
        return shopRepository.findAll();
    }

    @Override
    public List<Shop> saveAll(List<Shop> shopList) {
        return shopRepository.saveAll(shopList);
    }

    @Override
    public Shop save(Shop shop) {
        if (shop.getCreateTime() == null) {
            shop.setCreateTime(LocalDateTime.now());
        }
        shop.setUpdateTime(LocalDateTime.now());
        return shopRepository.save(shop);
    }

    @Override
    public Shop getShopById(Long id) {
        return shopRepository.getOne(id);
    }

}
