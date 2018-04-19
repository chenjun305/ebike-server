package com.ecgobike.pojo.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by ChenJun on 2018/3/11.
 */
@Data
public class UserUpdateParams extends AuthParams {
    private String nickname;
    private Byte gender;
    private String address;
    //private MultipartFile avatar;
}
