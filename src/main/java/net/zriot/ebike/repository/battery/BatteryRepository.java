package net.zriot.ebike.repository.battery;

import net.zriot.ebike.model.battery.Battery;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ChenJun on 2018/3/12.
 */
public interface BatteryRepository extends JpaRepository<Battery, Long> {
    Battery findOneBySn(String sn);
}
