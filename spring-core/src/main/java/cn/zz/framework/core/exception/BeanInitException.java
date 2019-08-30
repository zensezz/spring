package cn.zz.framework.core.exception;

import cn.zz.framework.core.exception.base.BaseException;
import cn.zz.framework.core.exception.base.BaseException;

public class BeanInitException extends BaseException {


	public BeanInitException(Class<?> clazz){
		super("Bean初始化失败 >>"+clazz.getName());
	}
	
	public BeanInitException(Class<?> clazz,Exception e){
		super("Bean初始化失败 >>"+clazz.getName(),e);
	}
}
