package net.zriot.ebike.model.shop;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "shop")
@Data
public class Shop implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String tel;
    private String address;
    private String openTime;
    private String description;
    private String latitude;
    private String longitude;
    private String geohash;
    private Integer batteryAvailable;
    private Byte status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
