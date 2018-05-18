package com.ecgobike.entity;

import com.ecgobike.common.enums.AppType;
import com.ecgobike.common.enums.OsType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/5/18.
 */
@Entity
@Table(name = "user_token")
@Data
public class UserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uid;
    private AppType appType;
    private String fcmToken;
    private OsType osType;
    private String apnsToken;
    private Integer status;
    private LocalDateTime refreshTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
