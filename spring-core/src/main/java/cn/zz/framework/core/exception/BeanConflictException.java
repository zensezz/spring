package cn.zz.framework.core.exception;

import cn.zz.framework.core.exception.base.BaseException;
import cn.zz.framework.core.exception.base.BaseException;

public class BeanConflictException extends BaseException {


	public BeanConflictException(String bean){
		super("Bean冲突 >>"+bean);
	}
	
	public BeanConflictException(String bean,Exception e){
		super("Bean冲突 >>"+bean, e);
	}
}
