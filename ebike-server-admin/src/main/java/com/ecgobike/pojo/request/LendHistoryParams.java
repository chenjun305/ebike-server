package com.ecgobike.pojo.request;

import com.ecgobike.common.annotation.NotNull;
import lombok.Data;

/**
 * Created by ChenJun on 2018/5/2.
 */
@Data
public class LendHistoryParams extends AuthParams {
    @NotNull
    private String batterySn;
}
