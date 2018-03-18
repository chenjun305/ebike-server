package net.zriot.ebike.service.ebike;

import net.zriot.ebike.entity.battery.Battery;
import net.zriot.ebike.entity.ebike.EBike;
import net.zriot.ebike.repository.ebike.EBikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Override
    public EBike findOneBySn(String sn) {
        return eBikeRepository.findOneBySn(sn);
    }

    @Override
    public EBike joinMembership(EBike eBike) {
        eBike.setIsMembership((byte)1);
        eBike.setMonthStartDate(LocalDate.now());
        eBike.setMonthEndDate(LocalDate.now().plusMonths(1));
        eBike.setUpdateTime(LocalDateTime.now());
        return eBikeRepository.save(eBike);
    }

    @Override
    public EBike renew(EBike eBike) {
        eBike.setMonthStartDate(LocalDate.now());
        eBike.setMonthEndDate(LocalDate.now().plusMonths(1));
        eBike.setUpdateTime(LocalDateTime.now());
        return eBikeRepository.save(eBike);
    }

    @Override
    public EBike changeToBattery(EBike eBike, Battery battery) {
        eBike.setBatterySn(battery.getSn());
        eBike.setUpdateTime(LocalDateTime.now());
        return eBikeRepository.save(eBike);
    }
}
