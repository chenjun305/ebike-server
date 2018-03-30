package com.ecgobike.controller;

import com.ecgobike.pojo.response.MessageDto;
import com.ecgobike.entity.User;
import com.ecgobike.service.UserService;
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
@RequestMapping("/user")
public class AdminUserController {

    @Autowired
    UserService userService;

    @RequestMapping("/list")
    public MessageDto list(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                    Pageable pageable
    ) {
        Page<User> users = userService.findAll(pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("users", users);
        return MessageDto.responseSuccess(data);

    }
}
