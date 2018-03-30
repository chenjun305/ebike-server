package com.ecgobike.service.impl;

import com.ecgobike.entity.Staff;
import com.ecgobike.repository.StaffRepository;
import com.ecgobike.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ChenJun on 2018/3/19.
 */
@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    StaffRepository staffRepository;

    @Override
    public Staff findOneByTel(String tel) {
        return staffRepository.findOneByTel(tel);
    }

    @Override
    public Staff findOneByUid(String uid) {
        return staffRepository.findOneByUid(uid);
    }

    @Override
    public Staff create(Staff staff) {
        return staffRepository.save(staff);
    }

    @Override
    public Page<Staff> findAll(Pageable pageable) {
        return staffRepository.findAll(pageable);
    }
}
