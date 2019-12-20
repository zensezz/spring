package cn.zz.framework.core.loader;

import cn.zz.framework.core.annotation.Around;
import cn.zz.framework.core.annotation.Arounds;
import cn.zz.framework.core.annotation.AutoBean;
import cn.zz.framework.core.constant.FrameworkConstant;
import cn.zz.framework.core.container.BeanContainer;
import cn.zz.framework.core.entity.AspectEntity;
import cn.zz.framework.core.loader.i.Loader;
import cn.zz.framework.core.util.MethodSignUtil;
import cn.zz.framework.core.util.PropertUtil;
import cn.zz.framework.core.util.StringUtil;
import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 切面加载器
 * 
 * @author zensezz
 *
 */
public class AspectLoader implements Loader {

	private static final Logger logger = Logger.getLogger(AspectLoader.class);
	@Override
	public void doLoader() throws Exception {
		if (StringUtil.isNullOrEmpty(BeanContainer.getClazzContainer())) {
			return;
		}
		for (Class<?> cla : BeanContainer.getClazzContainer()) {
			if (cla.isAnnotation()) {
				continue;
			}
			if (StringUtil.isNullOrEmpty(cla.getAnnotations())) {
				continue;
			}
			Annotation initBean = PropertUtil.getAnnotation(cla, AutoBean.class);
			if (initBean == null) {
				continue;
			}
			Method[] methods = cla.getDeclaredMethods();
			if (StringUtil.isNullOrEmpty(methods)) {
				continue;
			}
			for (Method method : methods) {
				if(Modifier.isStatic(method.getModifiers())||Modifier.isAbstract(method.getModifiers())){
					continue;
				}
				if(StringUtil.isNullOrEmpty(method.getAnnotations())){
					continue;
				}
				List<Annotation> arounds=PropertUtil.getAnnotations(method, Around.class);
				if (StringUtil.isNullOrEmpty(arounds)) {
					List<Annotation> aroundParents=PropertUtil.getAnnotations(method, Arounds.class);
					if(StringUtil.isNullOrEmpty(aroundParents)){
						continue;
					}
					arounds=new ArrayList<Annotation>();
					for(Annotation aroundParent:aroundParents){
						Annotation[] aroundTemps=PropertUtil.getAnnotationValue(aroundParent,"value");
						if(StringUtil.isNullOrEmpty(aroundTemps)){
							continue;
						}
						arounds.addAll(Arrays.asList(aroundTemps));
					}
				}
				for (Annotation around : arounds) {
					Map<String, Object> annotationValueMap=PropertUtil.getAnnotationValueMap(around);
					Class<?>[] annotationClass= (Class<?>[]) annotationValueMap.get("annotationClass");
					String classMappath=(String) annotationValueMap.get("classMappath");
					String methodMappath=(String) annotationValueMap.get("methodMappath");
					if (StringUtil.isAllNull(annotationClass, classMappath, methodMappath)) {
						continue;
					}
					Boolean masturbation=(Boolean) annotationValueMap.get("masturbation");
					logger.debug("初始化切面方法 >>"+ MethodSignUtil.getMethodKey(cla, method));
					AspectEntity aspectEntity = new AspectEntity();
					// 装载切面控制方法
					aspectEntity.setAnnotationClass(annotationClass);
					aspectEntity.setMethodMappath(methodMappath);
					aspectEntity.setClassMappath(classMappath);
					aspectEntity.setAspectInvokeMethod(method);
					aspectEntity.setMasturbation(masturbation);
					aspectEntity.setAspectClazz(cla);
					String methodKey = MethodSignUtil.getMethodUnionKey(method);
					FrameworkConstant.writeToAspectMap(methodKey, aspectEntity);
				}
			}
		}
	}

}
