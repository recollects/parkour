package com.alipay.parkour.dal;

import com.alipay.parkour.dal.dataObject.AsynExecutorCmdObject;

import java.util.List;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年03月31日 上午11:38
 */
public interface AsynExecutorCmdDAO {

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
     * @param businessNo
     * @param cmdType
     * @param tableNamePrefix
     * @return
     */
    AsynExecutorCmdObject selectByBusinessNoAndCmdType(String businessNo ,String cmdType,String tableNamePrefix);

    /**
     *
     * @param cmdType
     * @param limit
     * @return
     */
    List<AsynExecutorCmdObject> selectByCmdType(String cmdType,Integer limit,String tableNamePrefix);

}
