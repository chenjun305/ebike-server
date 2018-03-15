package net.zriot.ebike.repository.battery;

import net.zriot.ebike.entity.battery.Battery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ChenJun on 2018/3/12.
 */
@Repository
@Transactional
public interface BatteryRepository extends JpaRepository<Battery, Long> {
    Battery findOneBySn(String sn);
}
