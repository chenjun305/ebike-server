package net.zriot.ebike.pojo.request.user;

import lombok.Data;
import net.zriot.ebike.common.enums.Gender;

/**
 * Created by ChenJun on 2018/3/11.
 */
@Data
public class UserUpdateParams {
    private String nickname;
    private Byte gender;
    private String address;
}
