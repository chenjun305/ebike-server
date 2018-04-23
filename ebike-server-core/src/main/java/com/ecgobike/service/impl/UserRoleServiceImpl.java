package com.ecgobike.service.impl;

import com.ecgobike.common.enums.StaffRole;
import com.ecgobike.entity.UserRole;
import com.ecgobike.repository.UserRoleRepository;
import com.ecgobike.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by ChenJun on 2018/4/20.
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public UserRole findOneByUidAndRole(String uid, StaffRole role) {
        return userRoleRepository.findOneByUidAndRole(uid, role);
    }

    @Override
    public List<UserRole> findAllByUid(String uid) {
        return userRoleRepository.findAllByUid(uid);
    }

    @Override
    public UserRole create(String uid, StaffRole role) {
        UserRole userRole = new UserRole();
        userRole.setUid(uid);
        userRole.setRole(role);
        userRole.setStatus(1);
        userRole.setCreateTime(LocalDateTime.now());
        userRole.setUpdateTime(LocalDateTime.now());
        return userRoleRepository.save(userRole);
    }
}
