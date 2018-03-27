package net.zriot.ebike.service.ebike;

import net.zriot.ebike.entity.battery.Battery;
import net.zriot.ebike.entity.ebike.EBike;

import java.util.List;

/**
 * Created by ChenJun on 2018/3/11.
 */
public interface EBikeService {
    List<EBike> findAll();
    List<EBike> findAllByUid(String uid);
    EBike findOneBySn(String sn);
    EBike joinMembership(EBike eBike);
    EBike renew(EBike eBike);
    EBike changeToBattery(EBike eBike, Battery battery);
    EBike save(EBike eBike);
}
