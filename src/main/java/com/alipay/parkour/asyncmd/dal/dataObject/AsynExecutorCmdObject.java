package com.alipay.parkour.asyncmd.dal.dataObject;

import java.io.Serializable;
import java.util.Date;

import com.alipay.parkour.common.dal.BaseQuery;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年03月31日 上午11:31
 */
public class AsynExecutorCmdObject implements Serializable {

    private static final long serialVersionUID = -961168037924367101L;

    /**
     * ID
     */
    private String id;

    private Date gmtCreate;
    private Date gmtModified;
    private String creator;
    private String modifier;
    /**
     * 任务状态
     **/
    private String status;

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
     * 主机名
     */
    private String hostName;

    /**
     * 命令类型
     */
    private String cmdType;

    /**
     * 表名前缀
     */
    private String tableName;

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getCreator() {
        return creator;
    }

    public String getModifier() {
        return modifier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostName() {
        return hostName;
    }

    public String getCmdType() {
        return cmdType;
    }

    public void setCmdType(String cmdType) {
        this.cmdType = cmdType;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
