package cn.zz.framework.core.annotation;

import java.lang.annotation.*;

/**
 * 单个Around注解下连接符为"且"
 * 多个Around注解下连接符为"或"
 *
 * @Author zenghzong
 * @Since 2019/8/23
 * @Version 1.0
 */



@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME) 
@Repeatable(Arounds.class)
public @interface Around {

	/**
	 * 注解
	 * @return
	 */
    Class<?>[] annotationClass() default {};
    
    /**
     * 方法匹配(ant表达式)，可用MethodSignUtil.getMethodUnionKey(method) 获取方法标识对比
     * @return
     */
    String methodMappath() default "";
    
    /**
     * 类匹配(ant表达式)
     * @return
     */
    String classMappath() default "";
    
    /**
     * 是否拦截自类调用
     * @return
     */
    boolean masturbation() default true;
}
