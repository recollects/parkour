package com.alipay.parkour.asyncmd.dal.mysql;

import com.alipay.parkour.asyncmd.dal.AsynExecutorCmdDAO;
import com.alipay.parkour.asyncmd.dal.dataObject.AsynExecutorCmdObject;

import java.util.List;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年03月31日 上午11:40
 */
public class AsynExecutorMysqlCmdDAOImpl implements AsynExecutorCmdDAO{

    @Override
    public void save(AsynExecutorCmdObject cmdObject) {

    }

    @Override
    public void update(AsynExecutorCmdObject cmdObject) {

    }

    @Override
    public void backup(AsynExecutorCmdObject cmdObject) {

    }

    @Override
    public AsynExecutorCmdObject selectByBusinessNoAndCmdType(String businessNo, String cmdType, String tableName) {
        return null;
    }

    @Override
    public List<AsynExecutorCmdObject> selectByCmdType(String cmdType, Integer limit,String tableName) {
        return null;
    }
}
