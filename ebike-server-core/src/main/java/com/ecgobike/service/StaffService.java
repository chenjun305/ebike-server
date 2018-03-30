package com.ecgobike.service;

import com.ecgobike.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by ChenJun on 2018/3/19.
 */
public interface StaffService {
    Staff findOneByTel(String tel);
    Staff findOneByUid(String uid);
    Staff create(Staff staff);
    Page<Staff> findAll(Pageable pageable);
}
