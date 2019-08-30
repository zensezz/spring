package cn.zz.framework.jdbc.exception;

import cn.zz.framework.jdbc.exception.base.JdbcException;

/**
 * @Author zenghzong
 * @Since 2019/8/28
 * @Version 1.0
 */
public class PrimaryKeyException extends JdbcException {

	public PrimaryKeyException(){
		super();
	}
	
	public PrimaryKeyException(String msg){
		super(msg);
	}
	
	public PrimaryKeyException(String msg,Exception e){
		super(msg, e);
	}
}