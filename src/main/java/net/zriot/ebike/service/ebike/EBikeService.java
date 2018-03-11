package net.zriot.ebike.service.ebike;

import net.zriot.ebike.model.ebike.EBike;

import java.util.List;

/**
 * Created by ChenJun on 2018/3/11.
 */
public interface EBikeService {
    List<EBike> findAllByUid(String uid);
}
