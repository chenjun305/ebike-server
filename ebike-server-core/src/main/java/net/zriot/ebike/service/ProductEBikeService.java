package net.zriot.ebike.service;

import net.zriot.ebike.entity.ProductEBike;

import java.util.List;

/**
 * Created by ChenJun on 2018/3/28.
 */
public interface ProductEBikeService {
    List<ProductEBike> findAll();
}
