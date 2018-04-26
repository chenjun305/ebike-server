package com.ecgobike.helper;

import com.ecgobike.entity.Staff;
import com.ecgobike.service.StaffService;
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
    private static StaffService staffService;

    @Autowired
    public void setShopStaffService(StaffService staffService) {
        ShopStaffHelper.staffService = staffService;
    }

    /**
     * 警告:此cache中只能用作获取不变数据，不能用作获取动态数据
     */
    private static Cache<String, Staff> shopStaffCache = CacheBuilder.newBuilder().expireAfterAccess(10 * 60, TimeUnit.SECONDS).maximumSize(500).build();

    public static Staff findByUid(String uid) {
        Staff staff = shopStaffCache.getIfPresent(uid);
        if (staff == null) {
            staff = staffService.findOneByUid(uid);
            if (staff != null) {
                shopStaffCache.put(uid, staff);
            }
        }
        return staff;
    }
}
