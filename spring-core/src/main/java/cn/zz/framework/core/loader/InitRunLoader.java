package cn.zz.framework.core.loader;

import cn.zz.framework.core.bean.InitBean;
import cn.zz.framework.core.container.BeanContainer;
import cn.zz.framework.core.loader.i.Loader;
import cn.zz.framework.core.threadpool.ThreadBlockPool;
import cn.zz.framework.core.util.PrintException;
import cn.zz.framework.core.util.StringUtil;
import org.apache.log4j.Logger;


import java.util.ArrayList;
import java.util.List;

/**
 * 切面加载器
 * 
 * @author Coody
 *
 */
public class InitRunLoader implements Loader {

	private static final Logger logger = Logger.getLogger(InitRunLoader.class);

	@Override
	public void doLoader() throws Exception {
		List<Runnable> inits = new ArrayList<Runnable>();
		for (Object bean : BeanContainer.getBeans()) {
			if (bean instanceof InitBean) {
				// 初始化运行
				try {
					logger.debug("初始化执行 >>"+bean.getClass().getName());
					InitBean face = (InitBean) bean;
					inits.add(new Runnable() {
						@Override
						public void run() {
							try {
								face.init();
							} catch (Exception e) {
								PrintException.printException(logger, e);
							}
						}
					});
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
			}
		}
		if (!StringUtil.isNullOrEmpty(inits)) {
			new ThreadBlockPool().execute(inits);
		}
	}

}
