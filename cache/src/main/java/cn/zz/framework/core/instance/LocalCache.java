package cn.zz.framework.core.instance;

import cn.zz.framework.core.annotation.AutoBean;
import cn.zz.framework.core.instance.iface.ZZCacheFace;
import cn.zz.framework.core.pool.CacheThreadPool;
import cn.zz.framework.core.util.AntUtil;
import cn.zz.framework.core.util.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


/**
 * 内存缓存工具类
 * 
 * @author zensezz
 *
 */

@AutoBean
public class LocalCache implements ZZCacheFace {

	private static final ConcurrentHashMap<String, Object> CACHE_CONTAINER;
	static Object mutex = new Object();
	static {
		CACHE_CONTAINER = new ConcurrentHashMap<String, Object>();
	}

	/**
	 * 增加缓存对象
	 * 
	 * @param key
	 * @param value
	 * @param time
	 *            有效时间
	 */
	@Override
	public void setCache(String key, Object value, Integer time) {
		CACHE_CONTAINER.put(key, new CacheWrapper(time, value));
		CacheThreadPool.TASK_POOL.schedule(new TimeoutTimerTask(key, this), time * 1000, TimeUnit.MILLISECONDS);
	}

	/**
	 * 获取缓存KEY列表
	 * 
	 * @return 缓存key列表
	 */
	public Set<String> getCacheKeys() {
		return CACHE_CONTAINER.keySet();
	}

	/**
	 * 模糊获取缓存KEY
	 * 
	 * @param patton
	 * @return 缓存key列表
	 */
	public List<String> getKeysFuzz(String patton) {
		List<String> list = new ArrayList<String>();
		for (String key : CACHE_CONTAINER.keySet()) {
			if (AntUtil.isAntMatch(key, patton)) {
				list.add(key);
			}
		}
		if (StringUtil.isNullOrEmpty(list)) {
			return null;
		}
		return list;
	}

	/**
	 * 增加缓存对象
	 * 
	 * @param key
	 * @param value
	 */
	@Override
	public void setCache(String key, Object value) {
		CACHE_CONTAINER.put(key, new CacheWrapper(value));
	}

	/**
	 * 获取缓存对象
	 * 
	 * @param key
	 * @return 缓存内容
	 */
	@Override
	public <T> T getCache(String key) {
		CacheWrapper wrapper = (CacheWrapper) CACHE_CONTAINER.get(key);
		if (wrapper == null) {
			return null;
		}
		return (T) wrapper.getValue();
	}

	/**
	 * 检查是否含有制定key的缓冲
	 * 
	 * @param key
	 * @return true存在 ;false 不存在
	 */
	@Override
	public Boolean contains(String key) {
		return CACHE_CONTAINER.containsKey(key);
	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 */
	@Override
	public void delCache(String key) {
		CACHE_CONTAINER.remove(key);
	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 */
	public void delCacheFuzz(String key) {
		for (String tmpKey : CACHE_CONTAINER.keySet()) {
			if (tmpKey.contains(key)) {
				CACHE_CONTAINER.remove(tmpKey);
			}
		}
	}

	/**
	 * 获取缓存大小
	 */
	public int getCacheSize() {
		return CACHE_CONTAINER.size();
	}

	/**
	 * 清除全部缓存
	 */
	public void clearCache() {
		CACHE_CONTAINER.clear();
	}

	static class TimeoutTimerTask extends TimerTask {
		private String ceKey;
		private LocalCache cacheHandle;

		public TimeoutTimerTask(String key, LocalCache cacheHandle) {
			this.ceKey = key;
			this.cacheHandle=cacheHandle;
		}

		@Override
		public void run() {
			CacheWrapper cacheWrapper = (CacheWrapper) CACHE_CONTAINER.get(ceKey);
			if (cacheWrapper == null || cacheWrapper.getDate() == null) {
				return;
			}
			if (System.currentTimeMillis() < cacheWrapper.getDate().getTime()) {
				return;
			}
			cacheHandle.delCache(ceKey);
		}
	}

	private static class CacheWrapper {
		private Date date;
		private Object value;

		public CacheWrapper(int time, Object value) {
			this.date = new Date(System.currentTimeMillis() + time * 1000);
			this.value = value;
		}

		public CacheWrapper(Object value) {
			this.value = value;
		}

		public Date getDate() {
			return date;
		}

		public Object getValue() {
			return value;
		}
	}
}
