package net.zriot.ebike.service.staff;

import net.zriot.ebike.entity.staff.Staff;

import java.util.List;

/**
 * Created by ChenJun on 2018/3/19.
 */
public interface StaffService {
    Staff findOneByTel(String tel);
    Staff create(Staff staff);
    List<Staff> findAll();
}
