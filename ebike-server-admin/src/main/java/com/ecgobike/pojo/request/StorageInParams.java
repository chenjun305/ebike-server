package com.ecgobike.pojo.request;

import com.ecgobike.common.annotation.NotNull;
import com.ecgobike.common.annotation.Range;
import lombok.Data;

import java.util.List;

/**
 * Created by ChenJun on 2018/4/8.
 */
@Data
public class StorageInParams {
    @Range(Min = 1)
    private Long productId;

    @NotNull
    private List<String> snList;
}
