package com.alipay.parkour.asyncmd.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年03月31日 上午11:26
 */
public class AsynExecutorCmd {

    /**
     * 任务状态
     **/
    private AsynCmdStatusEnum status;

    /**
     * 重试次数
     */
    private int retryCount = 0;

    /**
     * 下次执行时间
     */
    private Date nextExecuteTime;

    /**
     * 外部业务数据编号
     */
    private String businessNo;

    /**
     * 执行上下文
     */
    private String context;
    /**
     * 执行异常信息
     */
    private String exceptionContext;

    /**
     * ID
     */
    private String id;

    /**
     * 主机名
     */
    private String hostname;

    /**
     * 命令类型
     */
    private String cmdType;

    public AsynCmdStatusEnum getStatus() {
        return status;
    }

    public void setStatus(AsynCmdStatusEnum status) {
        this.status = status;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public Date getNextExecuteTime() {
        return nextExecuteTime;
    }

    public void setNextExecuteTime(Date nextExecuteTime) {
        this.nextExecuteTime = nextExecuteTime;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getExceptionContext() {
        return exceptionContext;
    }

    public void setExceptionContext(String exceptionContext) {
        this.exceptionContext = exceptionContext;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getCmdType() {
        return cmdType;
    }

    public void setCmdType(String cmdType) {
        this.cmdType = cmdType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
