package cn.zz.framework.core.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Repeatable(CacheWipes.class)
public @interface CacheWipe {
	
	String key() ;
	
	String [] fields() default "";

}
