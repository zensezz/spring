package cn.zz.framework.elock;

import cn.zz.framework.elock.pointer.ELockerPointer;

/**
 * 分布式锁
 * 
 * @author zensezz
 *
 */
public class ELocker {
	

	/**
	 * 加锁
	 * 
	 * @param key
	 * @param expireSecond
	 * @throws InterruptedException
	 */
	public static void lock(String key, Integer expireSecond) throws InterruptedException {
		
		// 锁入列
		ELockerPointer.fallIn(key, expireSecond);
	}

	/**
	 * 释放锁
	 * 
	 * @param key
	 */
	public static void unLock(String key) {
		ELockerPointer.fallOut(key);
	}

}
