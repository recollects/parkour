package com.alipay.parkour.model;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月01日 下午12:12
 */
public class AsynExecutedConfig {

    private String cmdType;

    private Integer size;

    public void setCmdType(String cmdType) {
        this.cmdType = cmdType;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getCmdType() {
        return cmdType;
    }

    public Integer getSize() {
        return size;
    }
}
