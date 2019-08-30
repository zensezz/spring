package cn.zz.framework.core.exception;

import cn.zz.framework.core.exception.base.BaseException;

public class BeanNameCreateException extends BaseException {


	public BeanNameCreateException(Class<?> clazz){
		super("BeanName创建失败 >>"+clazz.getName());
	}
}
