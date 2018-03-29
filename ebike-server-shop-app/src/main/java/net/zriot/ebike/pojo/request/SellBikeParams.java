package net.zriot.ebike.pojo.request;

import lombok.Data;
import net.zriot.ebike.common.annotation.NotNull;

/**
 * Created by ChenJun on 2018/3/26.
 */
@Data
public class SellBikeParams extends AuthParams {
    @NotNull
    private String ebikeSn;
    @NotNull
    private String phoneNum;
    @NotNull
    private String realName;
    @NotNull
    private String idCardNum;
    @NotNull
    private String address;

}
