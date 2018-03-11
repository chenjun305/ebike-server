package net.zriot.ebike.service.ebike;

import net.zriot.ebike.common.constant.ErrorConstants;
import net.zriot.ebike.common.exception.GException;
import net.zriot.ebike.model.ebike.EBike;
import net.zriot.ebike.pojo.request.ebike.JoinMembershipParams;
import net.zriot.ebike.pojo.request.ebike.RenewParams;
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
    public EBike joinMembership(JoinMembershipParams params) throws GException {
        EBike ebike = eBikeRepository.findOneBySn(params.getEbikeSn());
        if (ebike == null) {
            throw new GException(ErrorConstants.NOT_EXIST_EBIKE);
        }
        ebike.setMembership(params.getMembership());
        ebike.setMonthFee(params.getMonthFee());
        ebike.setIsMembership((byte)1);
        ebike.setMonthStartDate(LocalDate.now());
        ebike.setMonthEndDate(LocalDate.now().plusMonths(1));
        ebike.setUpdateTime(LocalDateTime.now());
        ebike = eBikeRepository.save(ebike);
        return ebike;
    }

    @Override
    public EBike renew(RenewParams params) {
        EBike ebike = eBikeRepository.findOneBySn(params.getEbikeSn());
        ebike.setMonthFee(params.getMonthFee());
        ebike.setMonthStartDate(LocalDate.now());
        ebike.setMonthEndDate(LocalDate.now().plusMonths(1));
        ebike.setUpdateTime(LocalDateTime.now());
        ebike = eBikeRepository.save(ebike);
        return ebike;
    }
}
