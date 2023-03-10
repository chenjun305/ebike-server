package com.ecgobike.service;

import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by ChenJun on 2018/3/11.
 */
public interface EBikeService {
    Page<EBike> findAll(Pageable pageable);
    List<EBike> findAllByUid(String uid);
    EBike findOneBySn(String sn);
    EBike joinMembership(String ebikeSn) throws GException;
    EBike renew(String ebikeSn, int monthNum) throws GException;
    EBike sell(User user, String sn, Product product);
    EBike canLendBattery(String ebikeSn) throws GException;
    EBike lendBattery(EBike eBike);
}
