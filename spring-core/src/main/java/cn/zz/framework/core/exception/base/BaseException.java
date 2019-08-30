package cn.zz.framework.core.exception.base;


public class BaseException extends RuntimeException{

	public BaseException(){
		super();
	}
	
	public BaseException(String msg){
		super(msg);
	}
	
	public BaseException(String msg,Exception e){
		super(msg, e);
	}
}
