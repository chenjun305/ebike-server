package com.ecgobike.pojo.request;

import com.ecgobike.common.annotation.NotNull;
import com.ecgobike.common.annotation.Range;
import lombok.Data;
import com.ecgobike.common.annotation.StringLength;

/**
 * Created by ChenJun on 2018/3/20.
 */
@Data
public class StaffParams extends AuthParams {
    @StringLength(Min = 10, Max = 11)
    private String tel;

    @NotNull
    private String realName;
    private Byte gender;
    private String idCardNum;
    private Long shopId;
    @Range(Min = 0)
    private Byte role;
    private String staffNum;
    private String address;

}