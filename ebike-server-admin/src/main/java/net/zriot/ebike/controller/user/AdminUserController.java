package net.zriot.ebike.controller.user;

import net.zriot.ebike.entity.User;
import net.zriot.ebike.pojo.response.MessageDto;
import net.zriot.ebike.service.UserService;
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
@RequestMapping("/user")
public class AdminUserController {

    @Autowired
    UserService userService;

    @RequestMapping("/list")
    public MessageDto list() {
        List<User> users = userService.findAll();
        Map<String, Object> data = new HashMap<>();
        data.put("users", users);
        return MessageDto.responseSuccess(data);

    }
}
