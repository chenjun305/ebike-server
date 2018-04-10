package com.ecgobike.service.impl;

import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.*;
import com.ecgobike.repository.EBikeRepository;
import com.ecgobike.service.EBikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<EBike> findAll(Pageable pageable) {
        return eBikeRepository.findAll(pageable);
    }

    @Override
    public List<EBike> findAllByUid(String uid) {
        return eBikeRepository.findAllByUid(uid);
    }

    @Override
    public EBike findOneBySn(String sn) {
        return eBikeRepository.findOneBySn(sn);
    }

    @Override
    public EBike joinMembership(String ebikeSn) throws GException {
        // check ebike
        EBike eBike = eBikeRepository.findOneBySn(ebikeSn);
        if (eBike == null) {
            throw new GException(ErrorConstants.NOT_EXIST_EBIKE);
        }
        if (eBike.getIsMembership() != null && eBike.getIsMembership() == 1) {
            throw new GException(ErrorConstants.ALREADY_MEMBERSHIP);
        }

        eBike.setIsMembership((byte)1);
        eBike.setUpdateTime(LocalDateTime.now());
        return eBikeRepository.save(eBike);
    }

    @Override
    public EBike renew(String ebikeSn, int monthNum) throws GException {
        // check ebike
        EBike eBike = eBikeRepository.findOneBySn(ebikeSn);
        if (eBike == null) {
            throw new GException(ErrorConstants.NOT_EXIST_EBIKE);
        }
        if (eBike.getIsMembership() == null || eBike.getIsMembership() == 0) {
            throw new GException(ErrorConstants.NO_MEMBERSHIP);
        }
        if (eBike.getExpireDate() != null && eBike.getExpireDate().isAfter(LocalDate.now())) {
            throw new GException(ErrorConstants.ALREADY_RENEW);
        }

        eBike.setMonthNum(monthNum);
        eBike.setExpireDate(LocalDate.now().plusMonths(1));
        eBike.setUpdateTime(LocalDateTime.now());
        return eBikeRepository.save(eBike);
    }

    @Override
    public EBike sell(User user, EBike eBike) {
        eBike.setUid(user.getUid());
        eBike.setStatus((byte)1);
        eBike.setUpdateTime(LocalDateTime.now());
        return eBikeRepository.save(eBike);
    }

    @Override
    public EBike canLendBattery(String ebikeSn) throws GException {
        EBike eBike = eBikeRepository.findOneBySn(ebikeSn);
        // check for ebike
        if (eBike == null) {
            throw new GException(ErrorConstants.NOT_EXIST_EBIKE);
        }

        if (eBike.getIsMembership() == null || eBike.getIsMembership() == 0) {
            throw new GException(ErrorConstants.NO_MEMBERSHIP);
        }
        if (eBike.getExpireDate() == null || LocalDate.now().isAfter(eBike.getExpireDate())) {
            throw new GException(ErrorConstants.NEED_RENEW_MONTH_FEE);
        }
        if (eBike.getMonthNum() <= 0) {
            throw new GException(ErrorConstants.NEED_RENEW_MONTH_FEE);
        }
        return eBike;
    }

    @Override
    public EBike lendBattery(EBike eBike) {
        eBike.setMonthNum(eBike.getMonthNum()-1);
        eBike.setUpdateTime(LocalDateTime.now());
        return eBikeRepository.save(eBike);
    }
}
