package net.zriot.ebike.service;

import net.zriot.ebike.entity.User;
import net.zriot.ebike.pojo.request.Money;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    User login(String tel);
    User getUserByUid(String uid);
    User getUserByTel(String tel);
    User update(User user);
    User minusMoney(User user, BigDecimal fee);
    User addMoney(User user, Money money);
    List<User> findAll();
}
