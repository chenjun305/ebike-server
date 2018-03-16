package net.zriot.ebike.service.shop;

import net.zriot.ebike.entity.shop.Shop;
import net.zriot.ebike.repository.shop.ShopRepository;
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
}
