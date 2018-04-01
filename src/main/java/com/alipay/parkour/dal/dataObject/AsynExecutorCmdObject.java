package com.alipay.parkour.dal.dataObject;

import java.util.Date;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年03月31日 上午11:31
 */
public class AsynExecutorCmdObject {

    /**
     * 任务状态
     **/
    private String status;

    /**
     * 重试次数
     */
    private int executeTimes = 0;

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

    /**
     * 表名前缀
     */
    private String tableNamePrefix;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getExecuteTimes() {
        return executeTimes;
    }

    public void setExecuteTimes(int executeTimes) {
        this.executeTimes = executeTimes;
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

    public String getTableNamePrefix() {
        return tableNamePrefix;
    }

    public void setTableNamePrefix(String tableNamePrefix) {
        this.tableNamePrefix = tableNamePrefix;
    }
}
