package net.zriot.ebike.service;

import net.zriot.ebike.entity.Battery;
import net.zriot.ebike.entity.EBike;

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
    EBike save(EBike eBike);
}
