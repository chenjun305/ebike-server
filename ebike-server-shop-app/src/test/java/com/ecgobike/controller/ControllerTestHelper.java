package com.ecgobike.controller;

import com.ecgobike.common.constant.Constants;
import com.ecgobike.common.util.AES256;
import com.ecgobike.common.util.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by ChenJun on 2018/4/25.
 */
public class ControllerTestHelper {
    public static String uid = "f5bf85c2bfab4d53a22fd1afe71d140f";
    public static String token = "ty7B31bNIyUslWKw64HZ+/mZdk9RIiuDSLn31XXZIkKt2SApZDPCQviNlspvvbcJiIUL0eMaqMFvpDmr4ely9j8zkiV8cGJ/ZXi+BeLSTc4=";

    public static Map<String, String> secureParams(Map<String, String> params) {
        Map<String, String> secureParams = new HashMap<>();
        secureParams.putAll(params);
        secureParams.put("uid", uid);
        String sign = calculateSign(secureParams);
        secureParams.put("sign", sign);
        secureParams.put("token", token);

        return secureParams;
    }

    public static String calculateSign(Map<String, String> map) {
        TreeMap<String, String> checkMap = new TreeMap<>();
        System.out.println("=======request parameters=========");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + ":" + value);
            if ("sign".equals(key) || "token".equals(key)) {
                continue;
            }
            checkMap.put(key, value);
        }
        System.out.println("=======end=========");

        StringBuffer orginSource = new StringBuffer();
        for (Map.Entry<String, String> entry : checkMap.entrySet()) {
            String value = entry.getValue();
            orginSource.append(value);
        }
        String tokenDecrypt = "";
        try {
            tokenDecrypt = AES256.decrypt(token, Constants.TOKEN_SALT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String[] tCells = tokenDecrypt.split("[|]");
        String tokenSignMat = tCells[2];
        orginSource.append(tokenSignMat);

        return Utils.getMD5(orginSource.toString());
    }
}
