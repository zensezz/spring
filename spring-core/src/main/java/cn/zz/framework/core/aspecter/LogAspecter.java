package cn.zz.framework.core.aspecter;

import cn.zz.framework.core.annotation.Around;
import cn.zz.framework.core.annotation.AutoBuild;
import cn.zz.framework.core.annotation.LogHead;
import cn.zz.framework.core.entity.AspectPoint;
import cn.zz.framework.core.util.PropertUtil;
import cn.zz.framework.core.util.LoggerUtil;
import cn.zz.framework.core.util.StringUtil;

import java.lang.reflect.Method;

/**
 * @Author zenghzong
 * @Since 2019/8/23
 * @Version 1.0
 */
@AutoBuild
public class LogAspecter {

    @Around(annotationClass= LogHead.class)
    public Object logMonitor(AspectPoint able) throws Throwable{
        try{
            // AOP获取方法执行信息
            Method method = able.getAbler().getMethod();
            Class<?> clazz = PropertUtil.getClass(method);
            String module = LoggerUtil.getCurrLog();
            if (!StringUtil.isNullOrEmpty(module)) {
                module += "_";
            }
            String classLog = LoggerUtil.getClassLog(clazz);
            if (!StringUtil.isNullOrEmpty(classLog)){
                module += classLog;
            }
            if (StringUtil.isNullOrEmpty(module)){
                module+=".";
            }
            String methodLog = LoggerUtil.getMethodLog(method);
            if (!StringUtil.isNullOrEmpty(methodLog)) {
                module += methodLog;
            } else {
                module += method.getName();
            }
            LoggerUtil.writeLog(module);
            return able.invoke();
        }finally {
            LoggerUtil.minusLog();
        }
    }
}
 