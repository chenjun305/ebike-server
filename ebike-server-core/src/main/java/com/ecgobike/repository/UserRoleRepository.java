package com.ecgobike.repository;

import com.ecgobike.common.enums.StaffRole;
import com.ecgobike.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ChenJun on 2018/4/20.
 */
@Repository
@Transactional
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findOneByUidAndRole(String uid, StaffRole role);
    List<UserRole> findAllByUid(String uid);
}
