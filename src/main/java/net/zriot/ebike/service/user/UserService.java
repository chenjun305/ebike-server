package net.zriot.ebike.service.user;

import net.zriot.ebike.model.user.User;
import net.zriot.ebike.pojo.request.user.UserUpdateParams;

public interface UserService {
    User login(String tel);
    User getUserByUid(String uid);
    User updateByUid(String uid, UserUpdateParams params);
}
