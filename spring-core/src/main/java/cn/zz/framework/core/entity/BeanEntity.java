package cn.zz.framework.core.entity;

import cn.zz.framework.core.util.StringUtil;
import lombok.Data;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Data
public class BeanEntity extends BaseModel{

	private String fieldName;
	private Object fieldValue;
	private Class<?> fieldType;
	private Annotation[] fieldAnnotations;
	private Field sourceField;
	

	public <T extends Annotation> T getAnnotation(Class<?> clazz){
		if(StringUtil.isNullOrEmpty(fieldAnnotations)){
			return null;
		}
		for (Annotation annotation:fieldAnnotations) {
			if(clazz.isAssignableFrom(annotation.annotationType())){
				return (T) annotation;
			}
		}
		return null;
	}
	
}
