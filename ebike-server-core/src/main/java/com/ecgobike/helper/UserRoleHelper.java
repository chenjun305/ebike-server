package com.ecgobike.helper;

import com.ecgobike.common.enums.StaffRole;
import com.ecgobike.entity.UserRole;
import com.ecgobike.service.UserRoleService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by ChenJun on 2018/4/20.
 */
@Component
public class UserRoleHelper {
    private static UserRoleService userRoleService;

    @Autowired
    public void setUserRoleService(UserRoleService userRoleService) {
        UserRoleHelper.userRoleService = userRoleService;
    }

    /**
     * 警告:此cache中只能用作获取不变数据，不能用作获取动态数据
     */
    private static Cache<String, UserRole> userRoleCache = CacheBuilder.newBuilder().expireAfterAccess(10 * 60, TimeUnit.SECONDS).maximumSize(500).build();

    public static UserRole findByUidAndRole(String uid, StaffRole role) {
        String key = uid + "-" + role;
        UserRole userRole = userRoleCache.getIfPresent(key);
        System.out.println("key=" + key);
        if (userRole == null) {
            userRole = userRoleService.findOneByUidAndRole(uid, role);
            if (userRole != null) {
                userRoleCache.put(key, userRole);
            }
        }
        return userRole;
    }
}
