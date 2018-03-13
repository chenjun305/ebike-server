package net.zriot.ebike.pojo.request;

import lombok.Data;
import net.zriot.ebike.common.annotation.NotNull;

/**
 * Created by ChenJun on 2018/3/10.
 */
@Data
public class AuthParams {
    @NotNull
    private String uid;
    @NotNull
    private String sign;
    @NotNull
    private String token;
}
