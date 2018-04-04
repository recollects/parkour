package com.alipay.parkour.asyncmd.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author recollects
 * @version V1.0
 * @date 2018/4/4 18:19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AsynThreadPool {

    /**
     * 这类型命令线程池核心数
     *
     * @return
     */
    int coreSize() default 10;

    /**
     * 这类型命令线程池最大核心数
     *
     * @return
     */
    int maxSize() default 20;
}
