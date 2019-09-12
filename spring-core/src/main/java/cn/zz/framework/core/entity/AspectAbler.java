package cn.zz.framework.core.entity;

import cn.zz.framework.core.container.ThreadContainer;
import cn.zz.framework.core.enums.AspectConstant;
import lombok.Data;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author zenghzong
 * @Since 2019/8/23
 * @Version 1.0
 */
@Data
public class AspectAbler extends BaseModel implements Cloneable {

	// 业务bean
	private Object bean;

	// 业务方法
	private Method method;

	// 代理
	private MethodProxy proxy;

	// 业务所在class
	private Class<?> clazz;

	// 子切面
	private AspectAbler childAbler;

	// 切面方法
	private Method aspectMethod;

	// 切面bean
	private Object aspectBean;

	private Boolean masturbation = true;

	public Object invoke(AspectPoint point, Object[] params) throws Throwable {

		if (masturbation) {
			if (childAbler == null) {
				return proxy.invokeSuper(bean, params);
			}
			point.setAbler(childAbler);
			return childAbler.getAspectMethod().invoke(childAbler.getAspectBean(), point);
		}
		String aspectFlag = AspectConstant.THREAD_ENCRYPT_FLAG + "_" + clazz.getName();
		try {
			if (childAbler == null) {
				return proxy.invokeSuper(bean, params);
			}
			point.setAbler(childAbler);
			if (ThreadContainer.get(aspectFlag) != null) {
				return childAbler.invoke(point, params);
			}
			ThreadContainer.set(aspectFlag, this);
			return childAbler.getAspectMethod().invoke(childAbler.getAspectBean(), point);
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		} finally {
			ThreadContainer.remove(aspectFlag);
		}
	}

}
