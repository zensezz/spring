package cn.zz.framework.jdbc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记domain类的字段。
 *
 * @Author zenghzong
 * @Since 2019/8/28
 * @Version 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	/**
	 * 字段在数据库对应的列名
	 * @return
	 */
	 String value() default "";
}
