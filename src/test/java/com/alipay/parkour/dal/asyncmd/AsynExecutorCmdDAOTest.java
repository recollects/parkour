package com.alipay.parkour.dal.asyncmd;

import com.alibaba.fastjson.JSONObject;
import com.alipay.parkour.utils.SystemUtils;
import com.alipay.parkour.asyncmd.dal.AsynExecutorCmdDAO;
import com.alipay.parkour.asyncmd.dal.dataObject.AsynExecutorCmdDO;
import com.alipay.parkour.asyncmd.model.query.AsynExecutorCmdQuery;
import com.alipay.parkour.base.BaseJunit4Test;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
        AsynExecutorCmdDO cmdObject = new AsynExecutorCmdDO();
        cmdObject.setBusinessNo(UUID.randomUUID().toString());
        cmdObject.setCmdType("TP_S_1000:DEMO_B_ASYNEXECUTOR");

        Map<String,String> map = Maps.newHashMap();
        map.put("userName","yejiadong");
        map.put("age",27+"");
        map.put("password","123456");
        map.put("uuid", UUID.randomUUID().toString());

        cmdObject.setContext(JSONObject.toJSONString(map));
        cmdObject.setCreator("YE");
        cmdObject.setModifier("YE");
        cmdObject.setHostName(SystemUtils.getIP());
        cmdObject.setNextExecuteTime(new Date());
        cmdObject.setRetryCount(0);
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
        List<AsynExecutorCmdDO> cmdObjectList = asynExecutorCmdDAO.selectByCmdType(cmdObject);

        System.out.println(cmdObjectList);

    }

}
