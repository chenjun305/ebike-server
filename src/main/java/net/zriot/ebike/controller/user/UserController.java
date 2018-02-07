package net.zriot.ebike.controller.user;

import net.zriot.ebike.model.user.UserMain;
import net.zriot.ebike.pojo.response.MessageDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/login")
    public MessageDto login() {
        return MessageDto.responseSuccess();
    }

    @RequestMapping("/get")
    public MessageDto get() {
        UserMain userMain = new UserMain();
        userMain.setTel("13829780305");
        userMain.setMoney(192939);
        userMain.setIsMembership((byte) 1);
        return MessageDto.responseSuccess(userMain);
    }
}
