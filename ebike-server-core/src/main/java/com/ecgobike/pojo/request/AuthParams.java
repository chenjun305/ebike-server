package com.ecgobike.pojo.request;

import lombok.Data;
import com.ecgobike.common.annotation.NotNull;

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
