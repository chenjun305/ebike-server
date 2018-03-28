package net.zriot.ebike.controller.battery;

import net.zriot.ebike.common.annotation.AuthRequire;
import net.zriot.ebike.common.constant.ErrorConstants;
import net.zriot.ebike.common.enums.Auth;
import net.zriot.ebike.common.exception.GException;
import net.zriot.ebike.common.util.IdGen;
import net.zriot.ebike.entity.Battery;
import net.zriot.ebike.entity.EBike;
import net.zriot.ebike.entity.LendBattery;
import net.zriot.ebike.entity.User;
import net.zriot.ebike.pojo.request.AuthParams;
import net.zriot.ebike.pojo.request.battery.ChangeBatteryParams;
import net.zriot.ebike.pojo.response.MessageDto;
import net.zriot.ebike.service.LendBatteryService;
import net.zriot.ebike.service.BatteryService;
import net.zriot.ebike.service.EBikeService;
import net.zriot.ebike.service.UserService;
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

    @Autowired
    LendBatteryService lendBatteryService;

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
        if (eBike.getExpireDate() == null || LocalDate.now().isAfter(eBike.getExpireDate())) {
            throw new GException(ErrorConstants.NEED_RENEW_MONTH_FEE);
        }

        // check for battery
        Battery battery = batteryService.findOneBySn(params.getBatterySn());
        if (battery == null) {
            throw new GException(ErrorConstants.NOT_EXIST_BATTERY);
        }
        if (battery.getEbikeSn() != null) {
            throw new GException(ErrorConstants.NOT_RETURNED_BATTERY);
        }

        LendBattery lendBattery = new LendBattery();
        lendBattery.setSn(IdGen.genOrderSn());
        lendBattery.setBatterySn(battery.getSn());
        lendBattery.setEbikeSn(eBike.getSn());
        lendBattery.setLendTime(LocalDateTime.now());
        lendBattery.setUid(uid);
        lendBattery.setLendShopId(battery.getShopId());
        lendBattery.setStatus((byte)0);
        lendBattery = lendBatteryService.save(lendBattery);

        batteryService.changeToEBike(battery, eBike);

        Map<String, Object> data = new HashMap<>();
        data.put("paidAmount", 0);
        data.put("balance", user.getMoney());
        data.put("orderSn", lendBattery.getSn());
        data.put("orderTime", LocalDateTime.now());
        data.put("expireDate", eBike.getExpireDate());
        return MessageDto.responseSuccess(data);
    }
}
