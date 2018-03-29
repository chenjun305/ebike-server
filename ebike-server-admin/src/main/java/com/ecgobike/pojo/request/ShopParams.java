package com.ecgobike.pojo.request;

import lombok.Data;
import com.ecgobike.common.annotation.NotNull;

/**
 * Created by ChenJun on 2018/3/21.
 */
@Data
public class ShopParams {
    @NotNull
    private String name;
    @NotNull
    private String tel;
    @NotNull
    private String address;
    private String description;
    private String latitude;
    private String longitude;
    private String openTime;
    private String ownerUid;
}
