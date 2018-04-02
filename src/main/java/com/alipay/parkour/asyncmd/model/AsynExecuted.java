package com.alipay.parkour.asyncmd.model;

import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年03月31日 下午12:23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Service
public @interface AsynExecuted {

    String value() default "";
}
