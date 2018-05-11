package com.ecgobike.repository;

import com.ecgobike.common.enums.BookBatteryStatus;
import com.ecgobike.entity.BookBattery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by ChenJun on 2018/5/9.
 */
@Repository
@Transactional
public interface BookBatteryRepository extends JpaRepository<BookBattery, Long> {
    @Query(value = "select count(1) as num " +
            "from book_battery " +
            "where shop_id = ?1 and status = 1 and expire_time > CURRENT_TIMESTAMP",
            nativeQuery = true)
    Long countBookNumInShop(Long shopId);

    @Query(value = "select * " +
            "from book_battery " +
            "where shop_id = ?1 and status = 1 and expire_time > CURRENT_TIMESTAMP",
            nativeQuery = true)
    List<BookBattery> getByShopId(Long shopId);

    @Query(value = "select * " +
            "from book_battery " +
            "where ebike_sn = ?1 and status = 1 and expire_time > CURRENT_TIMESTAMP",
            nativeQuery = true)
    List<BookBattery> getByEbikeSn(String ebikeSn);

    @Query(value = "select * " +
            "from book_battery " +
            "where uid = ?1 and status = 1 and expire_time > CURRENT_TIMESTAMP",
            nativeQuery = true)
    List<BookBattery> getByUid(String uid);

    List<BookBattery> findAllByStatusAndExpireTimeBefore(BookBatteryStatus status, LocalDateTime time);
}
