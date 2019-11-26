package cn.zz.framework.elock.exception;

import cn.zz.framework.core.exception.base.BaseException;

public class JedisNotInitedException extends BaseException {

	public JedisNotInitedException(){
		super();
	}
	
	public JedisNotInitedException(String msg){
		super(msg);
	}
	
	public JedisNotInitedException(String msg,Exception e){
		super(msg, e);
	}
}
