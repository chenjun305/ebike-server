package com.ecgobike.pojo.request;

import com.ecgobike.common.annotation.NotNull;
import com.ecgobike.common.annotation.Range;
import com.ecgobike.common.enums.OsType;
import lombok.Data;

/**
 * Created by ChenJun on 2018/5/18.
 */
@Data
public class PushTokenParams extends AuthParams {
    @NotNull
    private String fcmToken;
    @Range(Min = 1, Max = 2)
    private Integer osType; // 1 Android 2 iOS

    private String apnsToken; // only iOS
}
