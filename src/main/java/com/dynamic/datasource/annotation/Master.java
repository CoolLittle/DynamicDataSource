package com.dynamic.datasource.annotation;

import java.lang.annotation.*;

/**
 * @description:
 * @author: caozheng
 * @date: 2019/9/26 11:07
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Documented
public @interface Master {

	 int value() default 1;
}
