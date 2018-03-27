package net.zriot.ebike.repository;

import net.zriot.ebike.entity.LendBattery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ChenJun on 2018/3/27.
 */
@Repository
@Transactional
public interface LendBatteryRepository extends JpaRepository<LendBattery, Long> {
}
