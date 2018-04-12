package com.ecgobike.pojo.request;

import com.ecgobike.common.annotation.Range;
import lombok.Data;

/**
 * Created by ChenJun on 2018/4/12.
 */
@Data
public class ProductParams extends AuthParams {
    @Range(Min = 1)
    private Long productId;
}
