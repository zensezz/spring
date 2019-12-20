package cn.zz.framework.core.aspect;

import java.lang.reflect.Method;

import cn.zz.framework.core.annotation.*;
import cn.zz.framework.core.entity.AspectPoint;
import cn.zz.framework.core.instance.iface.ZZCacheFace;
import cn.zz.framework.core.util.MethodSignUtil;
import cn.zz.framework.core.util.StringUtil;
import org.apache.log4j.Logger;


public class CacheAspect {

	ZZCacheFace cacheable;
	
	
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 写缓存操作
	 * @return 方法返回内容
	 * @throws Throwable
	 */
	@Around(annotationClass= CacheWrite.class)
	public Object cCacheWrite(AspectPoint aspectPoint) throws Throwable {
		Class<?> clazz = aspectPoint.getAbler().getClazz();
		Method method = aspectPoint.getAbler().getMethod();
		if (method == null) {
			return aspectPoint.invoke();
		}
		// 获取注解
		CacheWrite handle = method.getAnnotation(CacheWrite.class);
		if (handle == null) {
			return aspectPoint.invoke();
		}
		// 封装缓存KEY
		Object[] paras = aspectPoint.getParams();
		String key = handle.key();
		try {
			if (StringUtil.isNullOrEmpty(key)) {
				key = MethodSignUtil.getMethodKey(clazz, method);
			}
			if (StringUtil.isNullOrEmpty(handle.fields())) {
				String paraKey = MethodSignUtil.getBeanKey(paras);
				if (!StringUtil.isNullOrEmpty(paraKey)) {
					key += ":";
					key += paraKey;
				}
			}
			if (!StringUtil.isNullOrEmpty(handle.fields())) {
				key = MethodSignUtil.getFieldKey(clazz, method, paras, key, handle.fields());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Integer cacheTimer = ((handle.time() == 0) ? 24 * 3600 : handle.time());
		// 获取缓存
		try {
			Object result = cacheable.getCache(key);
			logger.debug("获取缓存 >>" + key + ",结果:" + result);
			if (!StringUtil.isNullOrEmpty(result)) {
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Object result = aspectPoint.invoke();
		if (result != null) {
			try {
				cacheable.setCache(key, result, cacheTimer);
				logger.debug("设置缓存 >>" + key + ",结果:" + result + ",缓存时间:" + cacheTimer);
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * 缓存清理
	 * @return 方法返回内容
	 * @throws Throwable
	 */
	@Around(annotationClass= CacheWipes.class)
	@Around(annotationClass= CacheWipe.class)
	public Object zCacheWipe(AspectPoint aspectPoint) throws Throwable {
		Class<?> clazz = aspectPoint.getAbler().getClazz();
		Method method = aspectPoint.getAbler().getMethod();
		if (method == null) {
			return aspectPoint.invoke();
		}
		Object[] paras = aspectPoint.getParams();
		Object result = aspectPoint.invoke();
		CacheWipe[] handles = method.getAnnotationsByType(CacheWipe.class);
		if (StringUtil.isNullOrEmpty(handles)) {
			return result;
		}
		for (CacheWipe handle : handles) {
			try {
				String key = handle.key();
				if (StringUtil.isNullOrEmpty(handle.key())) {
					key = (MethodSignUtil.getMethodKey(clazz, method));
				}
				if (!StringUtil.isNullOrEmpty(handle.fields())) {
					key = MethodSignUtil.getFieldKey(clazz, method, paras, key, handle.fields());
				}
				logger.debug("删除缓存 >>" + key);
				cacheable.delCache(key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
