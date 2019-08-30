package cn.zz.framework.core.entity;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 *  切面实体
 *
 * @Author zenghzong
 * @Since 2019/8/23
 * @Version 1.0
 */
@Data
public class AspectEntity extends BaseModel {

	private Class<?>[] annotationClass;

	private String methodMappath;

	private String classMappath;
	
	private Method aspectInvokeMethod;
	
	private Boolean masturbation;
	
	private Class<?> aspectClazz;
	
	

	@Override
	public String toString() {
		return "AspectEntity [annotationClass=" + Arrays.toString(annotationClass) + ", methodMappath=" + methodMappath
				+ ", classMappath=" + classMappath + ", aspectInvokeMethod=" + aspectInvokeMethod + "]";
	}

	
}
