package net.zriot.ebike.service.staff;

import net.zriot.ebike.entity.staff.Staff;

/**
 * Created by ChenJun on 2018/3/19.
 */
public interface StaffService {
    Staff findOneByTel(String tel);
}
