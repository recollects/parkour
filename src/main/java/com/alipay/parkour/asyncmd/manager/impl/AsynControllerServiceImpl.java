package com.alipay.parkour.asyncmd.manager.impl;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.alipay.parkour.asyncmd.manager.AsynControllerService;
import com.alipay.parkour.asyncmd.model.AsynCmdDefinition;
import com.alipay.parkour.asyncmd.model.AsynCmdDefinition.Builder;
import com.alipay.parkour.asyncmd.model.AsynController;
import com.alipay.parkour.asyncmd.model.AsynWork;
import com.alipay.parkour.context.ParkourApplicationContext;
import com.alipay.parkour.utils.ReflectionUtils;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author recollects
 * @version V1.0
 * @date 2018/4/3 15:42
 */
@Service
public class AsynControllerServiceImpl implements AsynControllerService {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(AsynControllerServiceImpl.class);

    /**
     * 命令容器[包涵所有的命令类型]
     */
    private Map<String, AsynCmdDefinition> asynCmdDefinitionMap = Maps.newConcurrentMap();

    /**
     * 命令列表
     */
    private List<String> asynExecutedCmds = Lists.newArrayList();

    /**
     * 初始化命令
     *
     * @return
     */
    @Override
    public void init(ApplicationContext applicationContext) {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(AsynController.class);

        if (MapUtils.isNotEmpty(beansWithAnnotation)) {
            Set<Entry<String, Object>> entries = beansWithAnnotation.entrySet();

            Iterator<Entry<String, Object>> iterator = entries.iterator();

            Iterators.all(iterator, new Predicate<Entry<String, Object>>() {
                @Override
                public boolean apply(Entry<String, Object> stringObjectEntry) {
                    addToAsynCmdMap(iterator.next());
                    return false;
                }
            });
            logger.info("装载成功命令列表:{}", asynExecutedCmds);
        }
    }

    /**
     * 对每个标识的AsynExecuted注解的类解析命令出来
     *
     * @param entry
     */
    private void addToAsynCmdMap(Map.Entry<String, Object> entry) {
        //标识了AsynExecuted注解的类
        Object value = entry.getValue();

        List<Method> methods = ReflectionUtils.findMethod(value.getClass(), true);

        Iterators.all(methods.iterator(), new Predicate<Method>() {
            @Override
            public boolean apply(Method input) {

                AsynWork asynconf = input.getAnnotation(AsynWork.class);

                if (asynconf != null) {
                    if (asynExecutedCmds.contains(asynconf.value())) {
                        throw new RuntimeException(asynconf.value() + "命令类型存在重复...");
                    }

                    //解析对任务的配置信息
                    AsynCmdDefinition builder = new Builder()
                        .cmdType(asynconf.value())
                        .coreSize(asynconf.coreSize())
                        .size(asynconf.size())
                        .maxSize(asynconf.maxSize())
                        .object(value)
                        .method(input)
                        .backup(asynconf.backup())
                        .builder();

                    //存在map里，KEY是命令类型，VALUE是这条命令的配置信息
                    asynCmdDefinitionMap.put(asynconf.value(), builder);
                    asynExecutedCmds.add(asynconf.value());
                    logger.debug("初始装载命令信息,cmdType:{},cmdDefinition:{}", asynconf.value(), builder);
                }
                return false;
            }
        });
    }

    /**
     * @return
     */
    @Override
    public Map<String, AsynCmdDefinition> getAsynCmdDefinitionMap() {
        return asynCmdDefinitionMap;
    }

    /**
     * @param cmd
     * @return
     */
    @Override
    public AsynCmdDefinition getAsynCmdDefinition(String cmd) {
        if (MapUtils.isNotEmpty(asynCmdDefinitionMap)) {
            return asynCmdDefinitionMap.get(cmd);
        }
        return null;
    }
}
