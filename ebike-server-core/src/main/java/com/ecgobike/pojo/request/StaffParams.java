package com.ecgobike.pojo.request;

import com.ecgobike.common.annotation.NotNull;
import com.ecgobike.common.annotation.Range;
import com.ecgobike.common.enums.StaffRole;
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
    @NotNull
    private Byte gender;
    @NotNull
    private String idCardNum;

    private Long shopId;

    @Range(Min = 1, Max = 3)
    private Integer role;

    private String staffNum;

    @NotNull
    private String address;
}
