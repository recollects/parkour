package com.alipay.parkour.bizlock.manager;

import com.alipay.parkour.bizlock.model.BizLockModel;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月02日 下午10:01
 */
public interface BizLockService {

    /**
     *
     * @param bizLockModel
     * @return
     */
    BizLockModel insertBizLock(BizLockModel bizLockModel);

    /**
     *
     * @param bizType
     * @param bizId
     * @return
     */
    BizLockModel selectBizLock(String bizType, String bizId);

    /**
     *
     * @param id
     * @return
     */
    BizLockModel forUpdateLocked(Long id);

}
