package com.ecgobike.common.annotation;

import java.lang.annotation.*;

/**
 * Created by ChenJun on 2018/3/10.
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotNull {
}
