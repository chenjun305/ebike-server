package com.ecgobike.common.util;

import java.security.SecureRandom;
import java.util.UUID;
import java.util.Date;

/**
 * Created by ChenJun on 2018/3/10.
 */
public class IdGen {
    private static SecureRandom random = new SecureRandom();

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

    public static String genOrderSn() {
        return "8" + new Date().getTime() + (int) (Math.random() * 100);
    }

  /*  public static void main (String[] args){
        System.out.println(IdGen.genOrderSn());
    }*/
}
