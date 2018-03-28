package net.zriot.ebike.service.impl;

import net.zriot.ebike.entity.ProductEBike;
import net.zriot.ebike.repository.ProductEBikeRepository;
import net.zriot.ebike.service.ProductEBikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ChenJun on 2018/3/28.
 */
@Service
public class ProductEBikeServiceImpl implements ProductEBikeService {
    @Autowired
    ProductEBikeRepository productEBikeRepository;

    @Override
    public List<ProductEBike> findAll() {
        return productEBikeRepository.findAll();
    }
}
