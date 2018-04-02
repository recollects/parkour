package com.alipay.parkour.bizlock.manager.impl;

import com.alipay.parkour.bizlock.dal.BizLockDAO;
import com.alipay.parkour.bizlock.manager.BizLockService;
import com.alipay.parkour.bizlock.model.BizLockModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月02日 下午10:01
 */
public class BizLockServiceImpl implements BizLockService {

    /**
     * logger
     */
    protected static final Logger logger = LoggerFactory.getLogger(BizLockServiceImpl.class);


    /**
     * 锁DAO
     */
    private BizLockDAO bizLockDAO;

    /**
     * 表前缀
     */
    private String tableNamePrefix;

    /**
     * 事务模板
     */
    private TransactionTemplate transactionNewTemplate;

    /**
     * 事务模板
     */
    private TransactionTemplate transactionTemplate;

    /**
     * 默认表名
     */
    private static final String DEFAULT_TABLE_NAME = "biz_lock";

    /**
     * 自定表名
     */
    private static  String BIZ_LOCK_TABLE_NAME;

    /**
     * 实例bean,初始化调用
     */
    public void start(){
        BIZ_LOCK_TABLE_NAME = StringUtils.isNotEmpty(tableNamePrefix) ? tableNamePrefix + DEFAULT_TABLE_NAME : DEFAULT_TABLE_NAME;
    }

    @Override
    public BizLockModel insertBizLock(BizLockModel bizLockModel) {
        return null;
    }

    @Override
    public BizLockModel selectBizLock(String bizType, String bizId) {
        return null;
    }

    @Override
    public BizLockModel forUpdateLocked(Long id) {
        return null;
    }

    public void setTableNamePrefix(String tableNamePrefix) {
        this.tableNamePrefix = tableNamePrefix;
    }

    public void setTransactionNewTemplate(TransactionTemplate transactionNewTemplate) {
        this.transactionNewTemplate = transactionNewTemplate;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
}
