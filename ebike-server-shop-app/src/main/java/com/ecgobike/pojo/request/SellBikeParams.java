package com.ecgobike.pojo.request;

import com.ecgobike.common.annotation.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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

//    @NotNull
//    private MultipartFile[] idCardFiles;


}
