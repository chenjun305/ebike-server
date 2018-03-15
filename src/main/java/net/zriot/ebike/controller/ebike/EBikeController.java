package net.zriot.ebike.controller.ebike;

import net.zriot.ebike.common.annotation.AuthRequire;
import net.zriot.ebike.common.enums.Auth;
import net.zriot.ebike.common.exception.GException;
import net.zriot.ebike.common.constant.ErrorConstants;
import net.zriot.ebike.entity.ebike.EBike;
import net.zriot.ebike.entity.user.User;
import net.zriot.ebike.pojo.request.AuthParams;
import net.zriot.ebike.pojo.request.ebike.JoinMembershipParams;
import net.zriot.ebike.pojo.request.ebike.RenewParams;
import net.zriot.ebike.pojo.response.MessageDto;
import net.zriot.ebike.service.ebike.EBikeService;
import net.zriot.ebike.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ebike")
public class EBikeController {

    @Autowired
    EBikeService eBikeService;

    @Autowired
    UserService userService;

    @PostMapping("/list")
    @AuthRequire(Auth.LOGIN)
    public MessageDto list(AuthParams authParams) {
        List<EBike> ebikes =  eBikeService.findAllByUid(authParams.getUid());
        Map<String, Object> data = new HashMap<>();
        data.put("ebikes", ebikes);
        return MessageDto.responseSuccess(data);
    }

    @PostMapping("/join")
    @AuthRequire(Auth.LOGIN)
    public MessageDto join(JoinMembershipParams params, AuthParams authParams) throws GException {
        String uid = authParams.getUid();

        BigDecimal membership = params.getMembership();
        BigDecimal monthFee = params.getMonthFee();
        BigDecimal fee = membership.add(monthFee);

        User user = userService.getUserByUid(uid);
        BigDecimal money = user.getMoney();
        if (money.compareTo(fee) == -1) {
            //throw new GException(ErrorConstants.LACK_MONEY);
            return new MessageDto(ErrorConstants.LACK_MONEY, "Lack Money");
        }
        EBike ebike = eBikeService.findOneBySn(params.getEbikeSn());
        if (ebike == null) {
            return new MessageDto(ErrorConstants.NOT_EXIST_EBIKE, "Not Exist EBike");
        }
        if (ebike.getIsMembership() == 1) {
            throw new GException(ErrorConstants.ALREADY_MEMBERSHIP);
            //return new MessageDto(ErrorConstants.ALREADY_MEMBERSHIP, "Already Membership");
        }
        user = userService.minusMoney(uid, membership.add(monthFee));

        ebike = eBikeService.joinMembership(params);

        Map<String, Object> data = new HashMap<>();
        data.put("balance", user.getMoney());
        data.put("orderSn", "2018030600001");
        data.put("orderTime", LocalDateTime.now());
        data.put("monthStartDate", ebike.getMonthStartDate());
        data.put("monthEndDate", ebike.getMonthEndDate());
        return MessageDto.responseSuccess(data);
    }

    @PostMapping("/renew")
    @AuthRequire(Auth.LOGIN)
    public MessageDto renew(RenewParams params, AuthParams authParams) throws GException {
        String uid = authParams.getUid();
        BigDecimal monthFee = params.getMonthFee();
        User user = userService.getUserByUid(uid);
        BigDecimal money = user.getMoney();
        if (money.compareTo(monthFee) == -1) {
            //throw new GException(ErrorConstants.LACK_MONEY);
            return new MessageDto(ErrorConstants.LACK_MONEY, "Lack Money");
        }
        EBike ebike = eBikeService.findOneBySn(params.getEbikeSn());
        if (ebike == null) {
            return new MessageDto(ErrorConstants.NOT_EXIST_EBIKE, "Not Exist EBike");
        }
        if (ebike.getIsMembership() == 0) {
            return new MessageDto(ErrorConstants.NO_MEMBERSHIP, "NO Membership");
        }
        if (ebike.getMonthEndDate().isAfter(LocalDate.now())) {
            return new MessageDto(ErrorConstants.ALREADY_RENEW, "Already Renew");
        }
        user = userService.minusMoney(uid, monthFee);
        ebike = eBikeService.renew(params);
        Map<String, Object> data = new HashMap<>();
        data.put("balance", user.getMoney());
        data.put("orderSn", "2018030600221");
        data.put("orderTime", LocalDateTime.now());
        data.put("monthStartDate", ebike.getMonthStartDate());
        data.put("monthEndDate", ebike.getMonthEndDate());
        return MessageDto.responseSuccess(data);
    }

}
