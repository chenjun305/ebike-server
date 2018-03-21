package net.zriot.ebike.pojo.request;

import lombok.Data;
import net.zriot.ebike.common.annotation.NotNull;

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
