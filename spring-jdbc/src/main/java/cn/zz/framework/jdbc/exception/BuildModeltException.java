package cn.zz.framework.jdbc.exception;

import cn.zz.framework.jdbc.exception.base.JdbcException;

/**
 * @Author zenghzong
 * @Since 2019/8/28
 * @Version 1.0
 */
public class BuildModeltException extends JdbcException {

	public BuildModeltException(){
		super();
	}
	
	public BuildModeltException(String msg){
		super(msg);
	}
	
	public BuildModeltException(String msg,Exception e){
		super(msg, e);
	}
}