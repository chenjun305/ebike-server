package com.ecgobike.common.annotation;

import java.lang.annotation.*;

/**
 * Created by ChenJun on 2018/3/10.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StringLength {
    int Min() default 0;//最小为0
    int Max() default Integer.MAX_VALUE;
    boolean canNull() default false;//是否可以为空，默认不能
}