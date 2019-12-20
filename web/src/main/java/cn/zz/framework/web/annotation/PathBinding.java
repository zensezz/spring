package cn.zz.framework.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.zz.framework.core.annotation.AutoBean;

/**
 * 地址映射
 * @author zz
 *
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME) 
@AutoBean
public @interface PathBinding {
	
	/**
	 * BeanName
	 * @return
	 */
	String [] value();

}
