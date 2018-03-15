package net.zriot.ebike.controller.battery;

import net.zriot.ebike.common.annotation.AuthRequire;
import net.zriot.ebike.common.constant.ErrorConstants;
import net.zriot.ebike.common.enums.Auth;
import net.zriot.ebike.common.exception.GException;
import net.zriot.ebike.entity.battery.Battery;
import net.zriot.ebike.entity.ebike.EBike;
import net.zriot.ebike.entity.user.User;
import net.zriot.ebike.pojo.request.AuthParams;
import net.zriot.ebike.pojo.request.battery.ChangeBatteryParams;
import net.zriot.ebike.pojo.response.MessageDto;
import net.zriot.ebike.service.battery.BatteryService;
import net.zriot.ebike.service.ebike.EBikeService;
import net.zriot.ebike.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/battery")
public class BatteryController {

    @Autowired
    EBikeService eBikeService;

    @Autowired
    BatteryService batteryService;

    @Autowired
    UserService userService;

    @PostMapping("/change")
    @AuthRequire(Auth.LOGIN)
    public MessageDto change(ChangeBatteryParams params, AuthParams authParams) throws GException {
        String uid = authParams.getUid();
        User user = userService.getUserByUid(uid);
        EBike eBike = eBikeService.findOneBySn(params.getEbikeSn());
        // check for ebike
        if (eBike == null) {
            throw new GException(ErrorConstants.NOT_EXIST_EBIKE);
        }
        if (!eBike.getUid().equals(uid)) {
            throw new GException(ErrorConstants.NOT_YOUR_EBIKE);
        }
        if (eBike.getIsMembership() == 0) {
            throw new GException(ErrorConstants.NO_MEMBERSHIP);
        }
        if (eBike.getMonthEndDate() == null || LocalDate.now().isAfter(eBike.getMonthEndDate())) {
            throw new GException(ErrorConstants.NEED_RENEW_MONTH_FEE);
        }
        if (eBike.getBatterySn() != null) {
            throw new GException(ErrorConstants.NOT_RETURN_OLD_BATTERY);
        }
        // check for battery
        Battery battery = batteryService.findOneBySn(params.getBatterySn());
        if (battery == null) {
            throw new GException(ErrorConstants.NOT_EXIST_BATTERY);
        }
        if (battery.getEbikeSn() != null) {
            throw new GException(ErrorConstants.NOT_RETURNED_BATTERY);
        }
        eBikeService.changeToBattery(eBike, battery);
        batteryService.changeToEBike(battery, eBike);
        Map<String, Object> data = new HashMap<>();
        data.put("paidAmount", 0);
        data.put("balance", user.getMoney());
        data.put("orderSn", "2018030600001");
        data.put("orderTime", LocalDateTime.now());
        data.put("expireDate", eBike.getMonthEndDate());
        return MessageDto.responseSuccess(data);
    }
}
