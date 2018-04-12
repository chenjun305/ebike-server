package com.ecgobike.pojo.request;

import com.ecgobike.common.annotation.NotNull;
import com.ecgobike.common.annotation.StringLength;
import lombok.Data;

/**
 * Created by ChenJun on 2018/4/12.
 */
@Data
public class CustomerParams extends AuthParams {
    @NotNull
    private String customerUid;
}
