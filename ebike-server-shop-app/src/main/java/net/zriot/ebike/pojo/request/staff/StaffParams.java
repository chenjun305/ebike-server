package net.zriot.ebike.pojo.request.staff;

import lombok.Data;
import net.zriot.ebike.common.annotation.NotNull;
import net.zriot.ebike.common.annotation.Range;
import net.zriot.ebike.common.annotation.StringLength;

/**
 * Created by ChenJun on 2018/3/20.
 */
@Data
public class StaffParams {
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
