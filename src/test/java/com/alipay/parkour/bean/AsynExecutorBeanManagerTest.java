package com.alipay.parkour.bean;

import com.alipay.parkour.base.BaseJunit4Test;
import com.alipay.parkour.context.ParkourApplicationContext;
import com.alipay.parkour.model.AsynExecuted;
import com.alipay.parkour.model.AsynExecutedHandle;
import com.alipay.parkour.model.AsynExecutorCmd;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年03月31日 下午2:45
 */
public class AsynExecutorBeanManagerTest extends BaseJunit4Test {

    @Test
    public void testInit() throws NoSuchMethodException {



    }

    @Test
    public void testGetBeanByType() {


        Map<String, List<AsynExecutorCmd>> cmdMap = Maps.newConcurrentMap();
        List<AsynExecutorCmd> lista = Lists.newArrayList();

        AsynExecutorCmd cmd0A = new AsynExecutorCmd();
        cmd0A.setBusinessNo("00001qwrqwerqwr");
        cmd0A.setContext("AAAAAAAAAAAAAAAAAAAAA");
        cmd0A.setCmdType("TP_S_1000:DEMO_A_ASYNEXECUTOR");
        AsynExecutorCmd cmd1A = new AsynExecutorCmd();
        cmd1A.setBusinessNo("00001asfwer");
        cmd1A.setCmdType("TP_S_1000:DEMO_A_ASYNEXECUTOR");
        cmd1A.setContext("AAAAAAAAAAAAqwerrrrrrrrrrrrrrrrrrrrrAAAAAAAAA");

        lista.add(cmd0A);
        lista.add(cmd1A);

        List<AsynExecutorCmd> listb = Lists.newArrayList();
        AsynExecutorCmd cmd0B = new AsynExecutorCmd();
        cmd0B.setBusinessNo("00002werqwer");
        cmd0B.setContext("BBBBBBBBBBBBBBBBBBBBBB");
        cmd0B.setCmdType("TP_S_1000:DEMO_B_ASYNEXECUTOR");

        AsynExecutorCmd cmd1B = new AsynExecutorCmd();
        cmd1B.setBusinessNo("00002--asfas");
        cmd1B.setContext("BBBBBBBBBBBBBeeeeeeeeeeeerwqr3qr3BBBBBBBBB");
        cmd1B.setCmdType("TP_S_1000:DEMO_B_ASYNEXECUTOR");
        listb.add(cmd0B);
        listb.add(cmd1B);

        cmdMap.put("TP_S_1000:DEMO_B_ASYNEXECUTOR", lista);
        cmdMap.put("TP_S_1000:DEMO_A_ASYNEXECUTOR", listb);

        Map<String, Object> map = Maps.newConcurrentMap();

        Map<String, Object> beansWithAnnotation = ParkourApplicationContext.getApplicationContext().getBeansWithAnnotation(AsynExecuted.class);

        System.out.println(beansWithAnnotation);

        Set<Map.Entry<String, Object>> entries = beansWithAnnotation.entrySet();

        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();

            Object value = entry.getValue();

            Method[] methods = value.getClass().getMethods();

            for (Method method : methods) {

                AsynExecutedHandle annotation = method.getAnnotation(AsynExecutedHandle.class);

                if (annotation != null && map.containsKey(annotation.value())) {
                    throw new RuntimeException(annotation.value() + "存在重复");
                }

                if (method.isAnnotationPresent(AsynExecutedHandle.class)) {

                    map.put(annotation.value(), method);

                    try {
                        List<AsynExecutorCmd> asynExecutorCmds = cmdMap.get(annotation.value());

                        for (AsynExecutorCmd cmd : asynExecutorCmds) {
                            method.invoke(value, cmd);
                        }

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

            }

        }

    }

}
