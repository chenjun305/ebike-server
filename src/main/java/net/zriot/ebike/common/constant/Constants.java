package net.zriot.ebike.common.constant;

import java.util.Random;

public interface Constants {
    Random random = new Random();
    String cache_prefix_sms_pin = "sms_pin_";

    /**生成sign添加的盐*/
    String SIGN_SALT = "6ee06a782b794b7989d758475f7d5ad9";

    /**生成token添加的盐*/
    String TOKEN_SALT = "UMUdfPjQDAK1cpUtO5eW8q2iQhljb8L6a7/k9yjdCOw=";

    /**30天的秒数*/
    int DAY30_2_SECOND = 60 * 60 * 24 * 30;
}
