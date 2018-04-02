package com.alipay.parkour.utils;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月01日 上午9:56
 */
public class ReflectionUtils {

    /**
     *
     */
    private static final List<String> EXCLUDE_METHOD = Lists.newArrayList("toString", "wait", "equals", "hashCode", "getClass", "notify", "notifyAll");


    /**
     * @param clazz
     * @return
     */
    public static List<Method> findMethod(Class<?> clazz) {
       return findMethod(clazz, true);
    }

    ;

    /**
     * @param clazz
     * @param isInclusion 是否包涵object方法
     * @return
     */
    public static List<Method> findMethod(Class<?> clazz, boolean isInclusion) {

        Preconditions.checkArgument(clazz != null, "Class is null");

//        if (clazz.isInterface()){
//            Method[] methods = clazz.getMethods();
//            List<Method> asList = Arrays.asList(methods);
//            return Sets.newHashSet(asList);
//        }

        Method[] methods = clazz.getMethods();

        List<Method> methodList = Arrays.asList(methods);

        Collection<Method> collection = Collections2.filter(methodList, new Predicate<Method>() {
            @Override
            public boolean apply(Method input) {

                if (isInclusion && !EXCLUDE_METHOD.contains(input.getName())) {
                    return true;
                }
                return false;
            }
        });

        return Lists.newArrayList(collection);
    }


}
