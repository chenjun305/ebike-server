package com.ecgobike.service;

import com.ecgobike.pojo.request.Money;
import com.ecgobike.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    User getOrCreate(String tel);
    User getUserByUid(String uid);
    User getUserByTel(String tel);
    User update(User user);
    User minusMoney(User user, BigDecimal fee);
    User addMoney(User user, Money money);
    Page<User> findAll(Pageable pageable);
}
