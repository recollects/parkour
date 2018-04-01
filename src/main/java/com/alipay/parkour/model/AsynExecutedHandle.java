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
     * @return
     */
    int size() default 20;

}
