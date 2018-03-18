package net.zriot.ebike.service.user;

import net.zriot.ebike.entity.user.User;

import java.math.BigDecimal;

public interface UserService {
    User login(String tel);
    User getUserByUid(String uid);
    User update(User user);
    User minusMoney(User user, BigDecimal fee);
}
