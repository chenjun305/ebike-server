package com.ecgobike.repository;

import com.ecgobike.entity.LendBattery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ChenJun on 2018/3/27.
 */
@Repository
@Transactional
public interface LendBatteryRepository extends JpaRepository<LendBattery, Long> {

    LendBattery findOneByBatterySnAndStatus(String batterySn, Byte status);
}
