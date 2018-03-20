package net.zriot.ebike.service.staff;

import net.zriot.ebike.entity.staff.Staff;
import net.zriot.ebike.repository.staff.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
