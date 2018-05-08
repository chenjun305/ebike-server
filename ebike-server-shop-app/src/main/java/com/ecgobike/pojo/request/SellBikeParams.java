package com.ecgobike.pojo.request;

import com.ecgobike.common.annotation.NotNull;
import com.ecgobike.common.annotation.StringLength;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by ChenJun on 2018/3/26.
 */
@Data
public class SellBikeParams extends AuthParams {
    @NotNull
    private String ebikeSn;

    @StringLength(Min = 10, Max = 11)
    private String phoneNum;

    @StringLength(Min = 4, Max = 4)
    private String pin;

    @NotNull
    private String realName;
    @NotNull
    private String idCardNum;
    @NotNull
    private String address;

//    @NotNull
//    private MultipartFile[] idCardFiles;


}
