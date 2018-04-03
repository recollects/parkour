package com.alipay.parkour.asyncmd.model.query;

import com.alipay.parkour.common.dal.BaseQuery;

/**
 * @author recollects
 * @version V1.0
 * @date 2018/4/4 17:11
 */
public class AsynExecutorCmdQuery extends BaseQuery {

    private String cmdType;
    private String tableName;

    public void setCmdType(String cmdType) {
        this.cmdType = cmdType;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCmdType() {
        return cmdType;
    }

    public String getTableName() {
        return tableName;
    }
}
