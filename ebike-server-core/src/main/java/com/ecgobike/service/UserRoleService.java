package com.ecgobike.service;

import com.ecgobike.common.enums.StaffRole;
import com.ecgobike.entity.UserRole;

import java.util.List;

/**
 * Created by ChenJun on 2018/4/20.
 */
public interface UserRoleService {
    UserRole findOneByUidAndRole(String uid, StaffRole role);
    List<UserRole> findAllByUid(String uid);
    UserRole create(String uid, StaffRole role);
}
