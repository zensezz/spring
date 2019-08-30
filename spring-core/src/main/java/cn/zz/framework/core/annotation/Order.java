package cn.zz.framework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author  zenghzong
 * @Since  2019/8/23
 * @Version  1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME) 
public @interface Order {
	int value() default Integer.MAX_VALUE-1;
}