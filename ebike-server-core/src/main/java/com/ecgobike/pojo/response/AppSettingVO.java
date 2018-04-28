package com.ecgobike.pojo.response;

import com.ecgobike.common.constant.Constants;
import com.ecgobike.common.constant.MonthNumFee;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.tomcat.util.bcel.Const;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by ChenJun on 2018/4/28.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppSettingVO {
    private BigDecimal membershipFee;
    private String currency;
    private String currencySymbol;
    private String serviceAgreementUrl;
    private Map<Integer, BigDecimal> monthNumFeeRule;

    public static AppSettingVO getDefault() {
        AppSettingVO settingVO = new AppSettingVO();
        settingVO.setMembershipFee(Constants.MEMBERSHIP_FEE);
        settingVO.setCurrency(Constants.CURRENCY);
        settingVO.setCurrencySymbol(Constants.CURRENCY_SYMBOL);
        settingVO.setServiceAgreementUrl("");
        settingVO.setMonthNumFeeRule(MonthNumFee.getRule());
        return settingVO;
    }
}
