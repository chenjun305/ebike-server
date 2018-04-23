package com.ecgobike.entity;

import com.ecgobike.common.enums.StaffRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/4/20.
 */
@Entity
@Table(name = "user_role")
@Data
public class UserRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uid;
    private StaffRole role;
    private Integer status;

    @JsonIgnore
    private LocalDateTime createTime;
    @JsonIgnore
    private LocalDateTime updateTime;
}
