package com.ecgobike.service.impl;

import com.ecgobike.common.enums.BookBatteryStatus;
import com.ecgobike.entity.BookBattery;
import com.ecgobike.entity.Shop;
import com.ecgobike.entity.User;
import com.ecgobike.repository.BookBatteryRepository;
import com.ecgobike.service.BookBatteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by ChenJun on 2018/5/9.
 */
@Service
public class BookBatteryServiceImpl implements BookBatteryService {

    @Autowired
    BookBatteryRepository bookBatteryRepository;


    @Override
    public Long countBookNumInShop(Long shopId) {
        Long num  = bookBatteryRepository.countBookNumInShop(shopId);
        System.out.print("num=" + num);
        return num;
    }

    @Override
    public List<BookBattery> getByEbikeSn(String ebikeSn) {
        return bookBatteryRepository.getByEbikeSn(ebikeSn);
    }

    @Override
    public List<BookBattery> getByUid(String uid) {
        return bookBatteryRepository.getByUid(uid);
    }

    @Override
    public BookBattery book(User user, String ebikeSn, Shop shop) {
        BookBattery bookBattery = new BookBattery();
        bookBattery.setUser(user);
        bookBattery.setEbikeSn(ebikeSn);
        bookBattery.setShop(shop);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireTime = now.plusMinutes(30);
        bookBattery.setBookTime(now);
        bookBattery.setExpireTime(expireTime);
        bookBattery.setStatus(BookBatteryStatus.NORMAL);
        bookBattery.setCreateTime(now);
        bookBattery.setUpdateTime(now);
        return bookBatteryRepository.save(bookBattery);
    }
}
