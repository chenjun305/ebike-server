package com.ecgobike.common.annotation;

import java.lang.annotation.*;

/**
 * Created by ChenJun on 2018/3/10.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Range {
    //二进制表示，00表示最大最小都不包含，10表示包含最小值不包含最大值，01表示包含最大值不包含最小值，11表示最大最小都包含
    byte boundary() default 0B11;
    double Min() default Double.MIN_VALUE;
    double Max() default Double.MAX_VALUE;
}
