package cn.zz.framework.elock.exception;

public class LockTimeOutException extends InterruptedException{

	public LockTimeOutException(String error) {
		super(error);
	}
}