package net.zriot.ebike.service.impl;

import net.zriot.ebike.entity.OrderSellEBike;
import net.zriot.ebike.repository.OrderSellEBikeRepository;
import net.zriot.ebike.service.OrderSellEBikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ChenJun on 2018/3/27.
 */
@Service
public class OrderSellEBikeServiceImpl implements OrderSellEBikeService {
    @Autowired
    OrderSellEBikeRepository orderSellEBikeRepository;

    @Override
    public OrderSellEBike save(OrderSellEBike orderSellEBike) {
        return orderSellEBikeRepository.save(orderSellEBike);
    }
}
