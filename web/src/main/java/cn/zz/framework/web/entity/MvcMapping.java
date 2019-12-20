package cn.zz.framework.web.entity;

import cn.zz.framework.core.entity.BaseModel;
import cn.zz.framework.core.entity.BeanEntity;
import cn.zz.framework.web.adapt.iface.WebParamsAdapt;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;

@Data
public class MvcMapping extends BaseModel {

	private String path;

	private Method method;

	private Object bean;

	private List<BeanEntity> paramTypes;

	private WebParamsAdapt paramsAdapt;


}