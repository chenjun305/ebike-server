package net.zriot.ebike.service.ebike;

import net.zriot.ebike.model.ebike.EBike;
import net.zriot.ebike.repository.ebike.EBikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ChenJun on 2018/3/11.
 */
@Service
public class EBikeServiceImpl implements EBikeService {
    @Autowired
    EBikeRepository eBikeRepository;
    @Override
    public List<EBike> findAllByUid(String uid) {
        return eBikeRepository.findAllByUid(uid);
    }
}
