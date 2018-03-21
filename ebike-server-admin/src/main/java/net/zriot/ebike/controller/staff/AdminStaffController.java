package net.zriot.ebike.controller.staff;

import net.zriot.ebike.entity.staff.Staff;
import net.zriot.ebike.pojo.response.MessageDto;
import net.zriot.ebike.service.staff.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ChenJun on 2018/3/21.
 */
@RestController
@RequestMapping("/staff")
public class AdminStaffController {

    @Autowired
    StaffService staffService;

    @RequestMapping("/list")
    public MessageDto list() {
        Map<String, Object> data = new HashMap<>();
        List<Staff> staffs = staffService.findAll();
        data.put("staffs", staffs);
        return MessageDto.responseSuccess(data);
    }
}
