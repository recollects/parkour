package com.alipay.parkour.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年03月31日 下午12:11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AsynExecutedHandle {

    /**
     * 命令类型[cmdType]
     *
     * @return
     */
    String value();

    /**
     * 批量处理任务数量
     *
     * @return
     */
    int size() default 20;

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

    /**
     * 是否需要备份
     * @return
     */
    boolean backup() default false;

}
