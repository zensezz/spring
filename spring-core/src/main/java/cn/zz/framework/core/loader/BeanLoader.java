package cn.zz.framework.core.loader;

import cn.zz.framework.core.annotation.AutoBean;
import cn.zz.framework.core.assember.BeanAssember;
import cn.zz.framework.core.container.BeanContainer;
import cn.zz.framework.core.loader.i.Loader;
import cn.zz.framework.core.util.PropertUtil;
import cn.zz.framework.core.util.StringUtil;

import java.lang.annotation.Annotation;

/**
 * Bean加载器
 * 
 * @author zensezz
 *
 */
public class BeanLoader implements Loader {

	@Override
	public void doLoader() throws Exception {
		if (StringUtil.isNullOrEmpty(BeanContainer.getClazzContainer())) {
			return;
		}
		for (Class<?> clazz : BeanContainer.getClazzContainer()) {
			if (clazz.isAnnotation()) {
				continue;
			}
			if (StringUtil.isNullOrEmpty(clazz.getAnnotations())) {
				continue;
			}
			Annotation AutoBean = PropertUtil.getAnnotation(clazz, AutoBean.class);
			if (StringUtil.isNullOrEmpty(AutoBean)) {
				continue;
			}
			BeanAssember.initBean(clazz);
		}
	}

}
