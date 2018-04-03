package com.alipay.parkour.asyncmd.model;

import java.lang.reflect.Method;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 异步命令配置
 *
 * @author recollects
 * @version V1.0
 * @date 2018年04月01日 下午12:12
 */
public class AsynCmdDefinition {

    private String cmdType;

    private Integer size;

    private Integer coreSize;

    private Integer maxSize;

    private Object object;

    private Method method;

    private boolean backup;

    private static ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();


    public static class Builder {

        private AsynCmdDefinition cmdDefinition;

        public Builder() {
            cmdDefinition = new AsynCmdDefinition();
        }

        public Builder cmdType(String cmdType) {
            cmdDefinition.setCmdType(cmdType);
            return this;
        }

        public Builder size(Integer size) {
            cmdDefinition.setSize(size);
            return this;
        }

        public Builder coreSize(Integer coreSize) {
            cmdDefinition.setCoreSize(coreSize);
            return this;
        }

        public Builder maxSize(Integer maxSize) {
            cmdDefinition.setMaxSize(maxSize);
            return this;
        }

        public Builder object(Object object) {
            cmdDefinition.setObject(object);
            return this;
        }

        public Builder method(Method method) {
            cmdDefinition.setMethod(method);
            return this;
        }
        public Builder backup(boolean backup) {
            cmdDefinition.setBackup(backup);
            return this;
        }

        public AsynCmdDefinition builder(){

            Preconditions.checkArgument(cmdDefinition.getCoreSize()>=0,"executor coreSize is null");
            Preconditions.checkArgument(cmdDefinition.getMaxSize()>=0,"executor getMaxSize is null");

            executor.setCorePoolSize(cmdDefinition.getCoreSize());
            executor.setMaxPoolSize(cmdDefinition.getMaxSize());
            return cmdDefinition;
        }
    }

    public ThreadPoolTaskExecutor getExecutor() {
        return executor;
    }

    public void setBackup(boolean backup) {
        this.backup = backup;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getSize() {
        return size;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getObject() {
        return object;
    }

    public Method getMethod() {
        return method;
    }

    public void setCmdType(String cmdType) {
        this.cmdType = cmdType;
    }

    public void setCoreSize(Integer coreSize) {
        this.coreSize = coreSize;
    }

    public Integer getCoreSize() {
        return coreSize;
    }

    public String getCmdType() {
        return cmdType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
