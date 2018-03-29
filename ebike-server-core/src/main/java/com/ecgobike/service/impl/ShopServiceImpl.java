package com.ecgobike.service.impl;

import com.ecgobike.entity.Shop;
import com.ecgobike.repository.ShopRepository;
import com.ecgobike.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Shop> findAll() {
        return shopRepository.findAll();
    }

    @Override
    public Shop create(Shop shop) {
        return shopRepository.save(shop);
    }
}
