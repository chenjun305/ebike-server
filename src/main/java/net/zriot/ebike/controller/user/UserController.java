package net.zriot.ebike.controller.user;

import net.zriot.ebike.pojo.response.MessageDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/login")
    public String login() {
        return MessageDto.responseSuccess().toString();
    }
}
