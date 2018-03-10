package net.zriot.ebike.common.util;

import net.zriot.ebike.common.constant.Constants;

/**
 * Created by ChenJun on 2018/3/10.
 */
public class AuthUtil {
    public static String buildToken(String uid, String signMat) {
        int time = DateUtils.getNow();
        String tokenSource = uid + "|" + time + "|" + signMat;

        try {
            String token = AES256.encrypt(tokenSource, Constants.TOKEN_SALT);
            return token;
        } catch (Exception e) {
            //LogHelp.doLogAppErr("aes256 build token fail!", e.getMessage());
        }

        return null;
    }

    public static String buildSignMaterial(String uid) {
        int time = DateUtils.getNow();
        String signSalt = Utils.getMD5(uid + time + Constants.SIGN_SALT);
        return signSalt;
    }
}
