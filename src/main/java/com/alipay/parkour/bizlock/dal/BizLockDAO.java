package com.alipay.parkour.bizlock.dal;

import com.alipay.parkour.bizlock.dal.dataObject.BizLockDO;
import com.alipay.parkour.common.dal.BaseDAO;

import java.util.List;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月02日 下午9:07
 */
public interface BizLockDAO extends BaseDAO{

    /**
     * @param tableName
     * @param id
     * @return
     */
    BizLockDO forUpdate(String tableName, Long id);

    /**
     * @param bizLockDO
     */
    void insert(BizLockDO bizLockDO);

    /**
     * @param bizLockDO
     */
    void update(BizLockDO bizLockDO);

    /**
     * @param bizLock
     * @return
     */
    List<BizLockDO> getByObject(BizLockDO bizLock);

    /**
     * @param tableName
     * @param id
     * @return
     */
    BizLockDO getById(String tableName, String id);

    /**
     * @param tableNamePrefix
     * @param id
     */
    void deleteById(String tableNamePrefix, Long id);
}
