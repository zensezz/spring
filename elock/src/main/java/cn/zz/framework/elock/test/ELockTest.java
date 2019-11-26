package cn.zz.framework.elock.test;

import cn.zz.framework.elock.ELocker;
import cn.zz.framework.elock.redis.ELockCache;

import java.util.ArrayList;
import java.util.List;


/**
 * 分布式锁测试
 * @author zensezz
 *
 * 2019年11月25日
 *
 */
public class ELockTest {

	//要加锁的key
	static String key = "TESTLOCK";

	static {
		//初始化jedis连接
		ELockCache.initJedisPool("127.0.0.1", 6379, "",10000);
	}

	public static void main(String[] args) {
		List<Thread> threads = new ArrayList<Thread>();
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					test();
				}
			});
			threads.add(thread);
		}
		//启动十个线程
		for (Thread thread : threads) {
			thread.start();
		}
	}

	//要锁的方法
	private static void test() {
		try {
			ELocker.lock(key, 100);
			for (int i = 0; i < 10; i++) {
				System.out.println(Thread.currentThread().getId() + ">>" + i);
				Thread.sleep(100l);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			ELocker.unLock(key);
		}
	}
}