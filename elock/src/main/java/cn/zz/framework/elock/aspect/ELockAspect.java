package cn.zz.framework.elock.aspect;

import cn.zz.framework.core.annotation.Around;
import cn.zz.framework.core.entity.AspectPoint;
import cn.zz.framework.core.util.MethodSignUtil;
import cn.zz.framework.core.util.StringUtil;
import cn.zz.framework.elock.ELocker;
import cn.zz.framework.elock.annotation.ELock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * 
 * @author zensezz
 * @date 2019年11月25日
 */
public class ELockAspect {

	/**
	 * 分布式锁切面，
	 * 
	 * @param point
	 * @return
	 * @throws Throwable
	 */
	@Around(annotationClass = ELock.class)
	public Object rdLock(AspectPoint point) throws Throwable {
		String lockName = null;
		// AOP启动监听
		Method method = point.getAbler().getMethod();
		Class<?> clazz = point.getAbler().getClazz();

		ELock dlock = method.getAnnotation(ELock.class);
		lockName = dlock.name();
		if (StringUtil.isNullOrEmpty(lockName)) {
			lockName = MethodSignUtil.getMethodKey(clazz, method);
		}
		Object[] paras = point.getParams();
		if (dlock.fields() != null && dlock.fields().length > 0) {
			lockName = MethodSignUtil.getFieldKey(clazz, method, paras, lockName, dlock.fields());
		}
		try {
			ELocker.lock(lockName, dlock.waitTime());
			return point.invoke();
		} catch (Exception e) {
			if (!dlock.igonreException()) {
				throw e;
			}
			return null;
		}finally{
			ELocker.unLock(lockName);
		}
	}

	/**
	 * 分布式锁切面，提供给第三方AOP工具
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	public Object rdLockForAspectj(ProceedingJoinPoint pjp) throws Throwable {
		// AOP获取方法执行信息
		Signature signature = pjp.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Class<?> clazz = pjp.getTarget().getClass();
		Method method = methodSignature.getMethod();

		String lockName = null;

		ELock dlock = method.getAnnotation(ELock.class);
		lockName = dlock.name();
		if (StringUtil.isNullOrEmpty(lockName)) {
			lockName = MethodSignUtil.getMethodKey(clazz, method);
		}
		Object[] paras = pjp.getArgs();
		if (dlock.fields() != null && dlock.fields().length > 0) {
			lockName = MethodSignUtil.getFieldKey(clazz, method, paras, lockName, dlock.fields());
		}
		try {
			ELocker.lock(lockName, dlock.waitTime());
			return pjp.proceed();
		} catch (Exception e) {
			if (!dlock.igonreException()) {
				throw e;
			}
			return null;
		}finally{
			ELocker.unLock(lockName);
		}
	}

}
