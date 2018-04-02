package com.alipay.parkour.bizlock.dal.dataObject;

import java.io.Serializable;
import java.util.Date;

/**
 * 业务锁DO
 * @author recollects
 * @version V1.0
 * @date 2018年04月02日 下午9:09
 */
public class BizLockDO implements Serializable {
    private static final long serialVersionUID = 1;


    /**
     * id
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改时间
     */
    private Date gmtModified;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 修改人
     */
    private String modifier;


    /**
     * 业务标识
     */
    private String bizId;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 山下文
     */
    private String context;

    /**
     * 状态
     */
    private String status;

    /**
     * 锁类型
     */
    private String lockType;

    /**
     * 加锁时间
     */
    private Date lockedTime;
    /**
     * 操作时间
     */
    private Date operateTime;
    /**
     * 主锁主键
     */
    private String masterLockId;

    /**
     * 表前缀
     */
    private String tableName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLockType() {
        return lockType;
    }

    public void setLockType(String lockType) {
        this.lockType = lockType;
    }

    public Date getLockedTime() {
        return lockedTime;
    }

    public void setLockedTime(Date lockedTime) {
        this.lockedTime = lockedTime;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getMasterLockId() {
        return masterLockId;
    }

    public void setMasterLockId(String masterLockId) {
        this.masterLockId = masterLockId;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }
}
