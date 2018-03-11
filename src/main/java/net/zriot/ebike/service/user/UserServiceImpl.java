package net.zriot.ebike.service.user;


import net.zriot.ebike.common.enums.Gender;
import net.zriot.ebike.common.util.IdGen;
import net.zriot.ebike.model.user.User;
import net.zriot.ebike.pojo.request.user.UserUpdateParams;
import net.zriot.ebike.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User login(String tel) {
        User user = userRepository.findOneByTel(tel);
        if (user == null) {
            // new user to register
            user = new User();
            user.setTel(tel);
            user.setUid(IdGen.uuid());
            user.setIsReal((byte)0);
            user.setGender(Gender.UNKNOWN.get());
            user.setMoney(new BigDecimal(0));
            user.setCurrency("USD");
            user.setStatus((byte)1);
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            return userRepository.save(user);
        }
        return user;
    }

    public User getUserByUid(String uid) {
        return userRepository.findOneByUid(uid);
    }

    @Override
    public User updateByUid(String uid, UserUpdateParams params) {
        User user = userRepository.findOneByUid(uid);
        if (params.getGender() != null) {
            user.setGender(params.getGender());
        }
        if (params.getAddress() != null) {
            user.setAddress(params.getAddress());
        }
        if (params.getNickname() != null) {
            user.setNickname(params.getNickname());
        }
        user.setUpdateTime(LocalDateTime.now());
        // need save ?
        userRepository.save(user);
        return user;
    }
}
