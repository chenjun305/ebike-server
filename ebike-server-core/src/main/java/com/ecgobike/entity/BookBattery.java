package com.ecgobike.entity;

import com.ecgobike.common.enums.BookBatteryStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/5/9.
 */
@Entity
@Table(name = "book_battery")
@Data
public class BookBattery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private String uid;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    private User user;

    private String ebikeSn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private LocalDateTime bookTime;
    private LocalDateTime expireTime;
    private String batterySn;
    private LocalDateTime lendTime;
    private LocalDateTime cancelTime;
    private BookBatteryStatus status;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
