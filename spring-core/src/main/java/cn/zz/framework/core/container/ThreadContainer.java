package cn.zz.framework.core.container;

import java.util.HashMap;
import java.util.Map;

/**
 *  线程池
 *
 * @Author zenghzong
 * @Since 2019/8/23
 * @Version 1.0
 */
public class ThreadContainer {
	
	private static ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<Map<String, Object>>();

	public static  void clear(){
		THREAD_LOCAL.remove();
	}
	
	public static <T> T get(String fieldName){
		initThreadContainer();
		return (T) THREAD_LOCAL.get().get(fieldName);
	}
	
	public static void initThreadContainer(){
		if(THREAD_LOCAL.get()!=null){
			return ;
		}
		THREAD_LOCAL.set(new HashMap<String, Object>());
	}
	
	public static void set(String fieldName,Object value){
		initThreadContainer();
		THREAD_LOCAL.get().put(fieldName, value);
	}
	
	public static void remove(String fieldName){
		initThreadContainer();
		THREAD_LOCAL.get().remove(fieldName);
	}
	
	public static boolean containsKey(String fieldName){
		initThreadContainer();
		return  THREAD_LOCAL.get().containsKey(fieldName);
	}
	
}
