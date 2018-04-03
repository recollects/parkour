package com.alipay.parkour.dal.asyncmd;

import com.alipay.parkour.asyncmd.dal.AsynExecutorCmdDAO;
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
    public void testGetById() {
        System.out.println(asynExecutorCmdDAO);
    }
}
