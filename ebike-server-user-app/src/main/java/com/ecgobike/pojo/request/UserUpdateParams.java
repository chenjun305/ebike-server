package com.ecgobike.pojo.request;

import lombok.Data;

/**
 * Created by ChenJun on 2018/3/11.
 */
@Data
public class UserUpdateParams extends AuthParams {
    private String nickname;
    private Byte gender;
    private String address;
}
