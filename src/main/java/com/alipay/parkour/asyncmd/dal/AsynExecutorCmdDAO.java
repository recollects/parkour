package com.alipay.parkour.asyncmd.dal;

import com.alipay.parkour.asyncmd.dal.dataObject.AsynExecutorCmdObject;
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
    void save(AsynExecutorCmdObject cmdObject);

    /**
     * 更新命令
     *
     * @param cmdObject
     */
    void update(AsynExecutorCmdObject cmdObject);

    /**
     * 数据备份
     */
    void backup(AsynExecutorCmdObject cmdObject);

    /**
     * 按业务编号命令类型查数据
     *
     * @param cmdObject
     * @return
     */
    AsynExecutorCmdObject selectByBusinessNoAndCmdType(AsynExecutorCmdQuery cmdObject);

    /**
     * @param cmdObject
     * @return
     */
    List<AsynExecutorCmdObject> selectByCmdType(AsynExecutorCmdQuery cmdObject);

}
