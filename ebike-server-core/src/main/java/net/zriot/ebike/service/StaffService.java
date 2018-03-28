package net.zriot.ebike.service;

import net.zriot.ebike.entity.Staff;

import java.util.List;

/**
 * Created by ChenJun on 2018/3/19.
 */
public interface StaffService {
    Staff findOneByTel(String tel);
    Staff findOneByUid(String uid);
    Staff create(Staff staff);
    List<Staff> findAll();
}
