package com.ecgobike.pojo.request;

import com.ecgobike.common.annotation.NotNull;
import com.ecgobike.common.annotation.StringLength;
import lombok.Data;

/**
 * Created by ChenJun on 2018/5/8.
 */
@Data
public class SellBikePinParams extends AuthParams {
    @StringLength(Min = 10, Max = 11)
    private String phoneNum;
}
