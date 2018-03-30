package com.ecgobike.controller;

import com.ecgobike.service.StaffService;
import com.ecgobike.entity.Staff;
import com.ecgobike.pojo.response.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public MessageDto list(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                    Pageable pageable
    ) {
        Map<String, Object> data = new HashMap<>();
        Page<Staff> staffs = staffService.findAll(pageable);
        data.put("staffs", staffs);
        return MessageDto.responseSuccess(data);
    }
}
