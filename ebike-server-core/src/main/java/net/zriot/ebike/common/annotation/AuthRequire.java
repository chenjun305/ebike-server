package net.zriot.ebike.common.annotation;

import net.zriot.ebike.common.enums.Auth;

import java.lang.annotation.*;

/**
 * Created by ChenJun on 2018/3/10.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthRequire {
    Auth value() default Auth.USER;
}
