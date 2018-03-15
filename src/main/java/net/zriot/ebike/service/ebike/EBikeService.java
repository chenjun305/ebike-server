package net.zriot.ebike.service.ebike;

import net.zriot.ebike.common.exception.GException;
import net.zriot.ebike.entity.battery.Battery;
import net.zriot.ebike.entity.ebike.EBike;
import net.zriot.ebike.pojo.request.ebike.JoinMembershipParams;
import net.zriot.ebike.pojo.request.ebike.RenewParams;

import java.util.List;

/**
 * Created by ChenJun on 2018/3/11.
 */
public interface EBikeService {
    List<EBike> findAllByUid(String uid);
    EBike findOneBySn(String sn);
    EBike joinMembership(EBike eBike, JoinMembershipParams params);
    EBike renew(RenewParams params);
    EBike changeToBattery(EBike eBike, Battery battery);
}
