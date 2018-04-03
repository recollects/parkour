package com.alipay.parkour.dal.asyncmd;

import java.util.Date;
import java.util.List;

import com.alipay.parkour.asyncmd.dal.AsynExecutorCmdDAO;
import com.alipay.parkour.asyncmd.dal.dataObject.AsynExecutorCmdObject;
import com.alipay.parkour.asyncmd.model.query.AsynExecutorCmdQuery;
import com.alipay.parkour.base.BaseJunit4Test;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author recollects
 * @version V1.0
 * @date 2018/4/3 11:13
 */
public class AsynExecutorCmdDAOTest extends BaseJunit4Test {

    @Autowired
    private AsynExecutorCmdDAO asynExecutorCmdDAO;

    @Test
    public void testSave() {
        AsynExecutorCmdObject cmdObject = new AsynExecutorCmdObject();
        cmdObject.setBusinessNo("DEMOA:test-001");
        cmdObject.setCmdType("TP_S_1000:DEMO_A_ASYNEXECUTOR");
        cmdObject.setContext("{}");
        cmdObject.setCreator("YE");
        cmdObject.setModifier("YE");
        cmdObject.setHostName("127.0.0.1");
        cmdObject.setNextExecuteTime(new Date());
        cmdObject.setRetryCount(8);
        cmdObject.setStatus("INIT");
        cmdObject.setTableName("parkour_asyn_executor_cmd");
        asynExecutorCmdDAO.save(cmdObject);
    }

    @Test
    public void testGetByCmdType(){
        AsynExecutorCmdQuery cmdObject = new AsynExecutorCmdQuery();
        cmdObject.setCmdType("TP_S_1000:DEMO_A_ASYNEXECUTOR");
        cmdObject.setTableName("parkour_asyn_executor_cmd");
        cmdObject.setPageSize(20);
        List<AsynExecutorCmdObject> cmdObjectList = asynExecutorCmdDAO.selectByCmdType(cmdObject);

        System.out.println(cmdObjectList);

    }


}
