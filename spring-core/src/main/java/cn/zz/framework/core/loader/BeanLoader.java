package cn.zz.framework.core.loader;

import cn.zz.framework.core.annotation.AutoBuild;
import cn.zz.framework.core.assember.BeanAssember;
import cn.zz.framework.core.container.BeanContainer;
import cn.zz.framework.core.loader.i.Loader;
import cn.zz.framework.core.util.PropertUtil;
import cn.zz.framework.core.util.StringUtil;

import java.lang.annotation.Annotation;

/**
 * Bean加载器
 * 
 * @author Coody
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
			Annotation autoBuild = PropertUtil.getAnnotation(clazz, AutoBuild.class);
			if (StringUtil.isNullOrEmpty(autoBuild)) {
				continue;
			}
			BeanAssember.initBean(clazz);
		}
	}

}
