package com.ecgobike.pojo.request.user;

import lombok.Data;

/**
 * Created by ChenJun on 2018/3/11.
 */
@Data
public class UserUpdateParams {
    private String nickname;
    private Byte gender;
    private String address;
}
