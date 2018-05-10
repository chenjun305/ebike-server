package com.ecgobike.pojo.request;

import com.ecgobike.common.annotation.Range;
import lombok.Data;

/**
 * Created by ChenJun on 2018/5/10.
 */
@Data
public class BookCancelParams extends AuthParams {
    @Range(Min = 1)
    private Long bookId;
}
