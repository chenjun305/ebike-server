package com.ecgobike.service.impl;


import com.ecgobike.common.enums.Gender;
import com.ecgobike.common.util.IdGen;
import com.ecgobike.pojo.request.Money;
import com.ecgobike.repository.UserRepository;
import com.ecgobike.service.UserService;
import com.ecgobike.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User getOrCreate(String tel) {
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
    public User getUserByTel(String tel) {
        return userRepository.findOneByTel(tel);
    }

    @Override
    public User update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userRepository.save(user);
        return user;
    }


    @Override
    public User minusMoney(User user, BigDecimal fee) {
        BigDecimal money = user.getMoney();
        user.setMoney(money.subtract(fee));
        user.setUpdateTime(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public User addMoney(User user, Money money) {
        user.setMoney(user.getMoney().add(money.getAmount()));
        user.setUpdateTime(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
