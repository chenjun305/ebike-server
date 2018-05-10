package com.ecgobike.service.impl;

import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.enums.BookBatteryStatus;
import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.BookBattery;
import com.ecgobike.entity.Shop;
import com.ecgobike.entity.User;
import com.ecgobike.repository.BookBatteryRepository;
import com.ecgobike.service.BookBatteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public BookBattery cancelBy(Long bookId, String uid) throws GException {
        BookBattery bookBattery = bookBatteryRepository.getOne(bookId);
        if (bookBattery == null) {
            throw new GException(ErrorConstants.NOT_EXIST_BOOKING);
        }
        if (!bookBattery.getUser().getUid().equals(uid)) {
            throw new GException(ErrorConstants.NOT_YOUR_BOOKING);
        }
        if (bookBattery.getStatus() == BookBatteryStatus.CANCELED) {
            throw new GException(ErrorConstants.BOOKING_ALREADY_CANCELED);
        }
        if (bookBattery.getStatus() == BookBatteryStatus.EXPIRED) {
            throw new GException(ErrorConstants.BOOKING_ALREADY_EXPIRED);
        }
        if (bookBattery.getStatus() == BookBatteryStatus.LENDED) {
            throw new GException(ErrorConstants.BOOKING_ALREADY_LEND);
        }
        LocalDateTime now = LocalDateTime.now();
        bookBattery.setCancelTime(now);
        bookBattery.setStatus(BookBatteryStatus.CANCELED);
        bookBattery.setUpdateTime(now);
        return bookBatteryRepository.save(bookBattery);
    }

    @Override
    public List<BookBattery> lendIfHasBooking(String ebikeSn, String batterySn) {
        List<BookBattery> list = bookBatteryRepository.getByEbikeSn(ebikeSn);
        if (list.size() > 0) {
            if (list.size() > 1) {
                System.out.print("Wrong: An Ebike has more than one booking, ebikeSn = " + ebikeSn );
            }
            List<BookBattery> saveList = list.stream().map(bookBattery -> {
                bookBattery.setBatterySn(batterySn);
                bookBattery.setLendTime(LocalDateTime.now());
                bookBattery.setStatus(BookBatteryStatus.LENDED);
                bookBattery.setUpdateTime(LocalDateTime.now());
                return bookBattery;
            }).collect(Collectors.toList());
            return bookBatteryRepository.saveAll(saveList);
        }
        return list;
    }

    @Override
    public List<BookBattery> adjustStatus() {
        LocalDateTime now = LocalDateTime.now();
        List<BookBattery> list = bookBatteryRepository.findAllByStatusAndExpireTimeBefore(BookBatteryStatus.NORMAL, now);
        List<BookBattery> saveList = list.stream().map(bookBattery -> {
            bookBattery.setStatus(BookBatteryStatus.EXPIRED);
            bookBattery.setUpdateTime(now);
            return bookBattery;
        }).collect(Collectors.toList());
        return bookBatteryRepository.saveAll(saveList);
    }
}
