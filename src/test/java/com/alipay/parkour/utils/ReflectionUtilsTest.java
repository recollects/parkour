package com.alipay.parkour.utils;

import com.alipay.parkour.bean.DemoAAsynExecutor;
import com.alipay.parkour.bean.DemoBAsynExecutor;
import com.alipay.parkour.model.AsynExecutedHandle;
import com.alipay.parkour.model.AsynExecutorCmd;

import java.lang.reflect.Method;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月01日 上午10:19
 */
public class ReflectionUtilsTest {

    public static void main(String[] args) throws  Exception{

//        classlass<? super DemoAAsynExecutor> superclass = DemoAAsynExecutor.class.getSuperclass();
//
//        Method[] methods = superclass.getMethods();
//
//        for(Method method:methods){
//
//            System.out.println(method.getName());
//        }

//        Set<Method> method = ReflectionUtils.findMethod(DemoAAsynExecutor.class, true);

//        System.out.println(method);

        Method doWork = DemoAAsynExecutor.class.getMethod("doWork", AsynExecutorCmd.class);
        Method doWork1 = DemoBAsynExecutor.class.getMethod("doWork", AsynExecutorCmd.class);

        AsynExecutedHandle annotation = doWork.getAnnotation(AsynExecutedHandle.class);
        AsynExecutedHandle annotation1 = doWork1.getAnnotation(AsynExecutedHandle.class);
        System.out.println(annotation==annotation1);

    }

}
