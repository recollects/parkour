package com.alipay.parkour.asyncmd.model;

/**
 * 任务状态
 *
 * @author recollects
 * @version V1.0
 * @date 2018年03月31日 上午11:19
 */
public enum AsynCmdStatusEnum {

    /**
     * 初始化
     */
    INIT,

    /**
     * 处理中
     */
    PROCESSING,

    /**
     * 停止
     */
    STOP,

    /**
     * 错误,需要人工来处理
     */
    ERROR,

    /**
     * 处理环节出现异常
     */
    EXCEPTION,

    /**
     * 处理完成
     */
    FINISHED;


}
