package net.zriot.ebike.service.user;

import net.zriot.ebike.model.user.User;

public interface UserService {
    User login(String tel);
    User getUserByUid(String uid);
}
