package net.zriot.ebike.common.cache;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class StringCacheService {

    private static StringRedisTemplate stringRedisTemplate;

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        StringCacheService.stringRedisTemplate = stringRedisTemplate;
    }
    public static void set(String key, String value, long time) {
        stringRedisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    public static String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public static boolean del(String key) {
        return stringRedisTemplate.delete(key);
    }

    public static boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }
}
