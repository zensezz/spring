package cn.zz.framework.core.exception;


import cn.zz.framework.core.exception.base.BaseException;
import cn.zz.framework.core.util.StringUtil;

import java.util.Collection;

public class MappedExecutableException extends BaseException {

	public MappedExecutableException(Class<?> clazz, Collection<String> parameters) {
		super("未找到匹配的构造函数>>" + clazz + " build from (" + StringUtil.collectionMosaic(parameters, ",") + ")");
	}
}
