package com.ecgobike.helper;

import com.ecgobike.entity.ShopStaff;
import com.ecgobike.service.ShopStaffService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

/**
 * Created by ChenJun on 2018/4/20.
 */
@Component
public class ShopStaffHelper {
    private static ShopStaffService shopStaffService;

    @Autowired
    public void setShopStaffService(ShopStaffService shopStaffService) {
        ShopStaffHelper.shopStaffService = shopStaffService;
    }

    /**
     * 警告:此cache中只能用作获取不变数据，不能用作获取动态数据
     */
    private static Cache<String, ShopStaff> shopStaffCache = CacheBuilder.newBuilder().expireAfterAccess(10 * 60, TimeUnit.SECONDS).maximumSize(500).build();

    public static ShopStaff findByUid(String uid) {
        ShopStaff shopStaff = shopStaffCache.getIfPresent(uid);
        if (shopStaff == null) {
            shopStaff = shopStaffService.findOneByUid(uid);
            if (shopStaff != null) {
                shopStaffCache.put(uid, shopStaff);
            }
        }
        return shopStaff;
    }
}
