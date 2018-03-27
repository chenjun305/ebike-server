package net.zriot.ebike.entity.staff;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/3/19.
 */
@Entity
@Table(name = "staff")
@Data
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uid;
    private String tel;
    private String avatar;

    private String realName;
    private Byte gender;
    private String idCardNum;
    private String idCardPics;

    private Long shopId;
    private Byte role;
    private String staffNum;

    private String address;

    private Byte status;

    @JsonIgnore
    private LocalDateTime createTime;
    @JsonIgnore
    private LocalDateTime updateTime;

}