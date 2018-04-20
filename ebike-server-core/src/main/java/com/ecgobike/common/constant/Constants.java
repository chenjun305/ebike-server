package com.ecgobike.common.constant;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public interface Constants {
    Random random = new Random();
    String cache_prefix_sms_pin = "sms_pin_";

    /**生成sign添加的盐*/
    String SIGN_SALT = "6ee06a782b794b7989d758475f7d5ad9";

    /**生成token添加的盐*/
    String TOKEN_SALT = "UMUdfPjQDAK1cpUtO5eW8q2iQhljb8L6a7/k9yjdCOw=";

    /**7天的秒数*/
    int DAY7_2_SECOND = 60 * 60 * 24 * 7;

    // App Setting
    BigDecimal MEMBERSHIP_FEE = new BigDecimal(20.0);
    String CURRENCY = "USD";
    String CURRENCY_SYMBOL = "$";

    // Tencent COS File storage
    String USER_AVATAR_URL_PREFIX = "http://ebike-user-avatar-1251198400.cosgz.myqcloud.com/";
    String USER_IDCARD_URL_PREFIX = "http://ebike-user-idcard-1251198400.cosgz.myqcloud.com/";
}
