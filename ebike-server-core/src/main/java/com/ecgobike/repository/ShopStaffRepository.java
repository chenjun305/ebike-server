package com.ecgobike.repository;

import com.ecgobike.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ChenJun on 2018/4/20.
 */
@Repository
@Transactional
public interface ShopStaffRepository extends JpaRepository<Staff, Long> {
    Staff findOneByUid(String uid);
}
