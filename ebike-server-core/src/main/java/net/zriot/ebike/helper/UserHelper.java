package net.zriot.ebike.helper;

import net.zriot.ebike.entity.User;
import net.zriot.ebike.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.TimeUnit;


/**
 * Created by ChenJun on 2018/3/10.
 */
@Component
public class UserHelper {

    private static UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        UserHelper.userService = userService;
    }

    /**
     * 警告:此cache中的userMain只能用作获取不变数据，不能用作获取动态数据，比如用户金额，必须通过service获取
     */
    private static Cache<String, User> userCache = CacheBuilder.newBuilder().expireAfterAccess(10 * 60, TimeUnit.SECONDS).maximumSize(500).build();

    public static User findUserByUid(String uid) {
        User user = userCache.getIfPresent(uid);
        if (user == null) {
            user = userService.getUserByUid(uid);
            if (user != null) {
                userCache.put(uid, user);
            }
        }
        return user;
    }
}
