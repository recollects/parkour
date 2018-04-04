package com.alipay.parkour.asyncmd.dal;

import com.alipay.parkour.asyncmd.dal.dataObject.AsynExecutorCmdDO;
import com.alipay.parkour.asyncmd.model.query.AsynExecutorCmdQuery;
import com.alipay.parkour.common.dal.BaseDAO;

import java.util.List;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年03月31日 上午11:38
 */
public interface AsynExecutorCmdDAO extends BaseDAO{

    /**
     * 保存一条命令
     *
     * @param cmdObject
     */
    void save(AsynExecutorCmdDO cmdObject);

    /**
     * 更新命令
     *
     * @param cmdObject
     */
    void update(AsynExecutorCmdDO cmdObject);

    /**
     * 数据备份
     */
    void backup(AsynExecutorCmdDO cmdObject);

    /**
     * 按业务编号命令类型查数据
     *
     * @param cmdObject
     * @return
     */
    AsynExecutorCmdDO selectByBusinessNoAndCmdType(AsynExecutorCmdQuery cmdObject);

    /**
     * @param cmdObject
     * @return
     */
    List<AsynExecutorCmdDO> selectByCmdType(AsynExecutorCmdQuery cmdObject);

}
