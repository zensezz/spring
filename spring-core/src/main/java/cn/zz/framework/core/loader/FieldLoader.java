package cn.zz.framework.core.loader;


import cn.zz.framework.core.assember.BeanAssember;
import cn.zz.framework.core.container.BeanContainer;
import cn.zz.framework.core.loader.i.Loader;

/**
 * 字段加载器
 * 
 * @author Coody
 *
 */
public class FieldLoader implements Loader {

	@Override
	public void doLoader() throws Exception {
		for (Object bean : BeanContainer.getBeans()) {
			BeanAssember.initField(bean);
		}
	}

}
