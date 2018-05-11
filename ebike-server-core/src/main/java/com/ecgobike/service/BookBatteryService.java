package com.ecgobike.service;

import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.BookBattery;
import com.ecgobike.entity.Shop;
import com.ecgobike.entity.User;

import java.util.List;

/**
 * Created by ChenJun on 2018/5/9.
 */
public interface BookBatteryService {
    Long countBookNumInShop(Long shopId);
    List<BookBattery> getByShopId(Long shopId);
    List<BookBattery> getByEbikeSn(String ebikeSn);
    List<BookBattery> getByUid(String uid);
    BookBattery book(User user, String ebikeSn, Shop shop);
    BookBattery cancelBy(Long bookId, String uid) throws GException;
    List<BookBattery> lendIfHasBooking(String ebikeSn, String batterySn);
    List<BookBattery> adjustStatus();
}
