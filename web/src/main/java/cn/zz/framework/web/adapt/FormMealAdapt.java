package cn.zz.framework.web.adapt;

import cn.zz.framework.core.constant.InsideTypeConstant;
import cn.zz.framework.core.entity.BaseModel;
import cn.zz.framework.core.entity.BeanEntity;
import cn.zz.framework.core.util.PropertUtil;
import cn.zz.framework.core.util.StringUtil;
import cn.zz.framework.web.adapt.iface.WebParamsAdapt;
import cn.zz.framework.web.annotation.ParamName;
import cn.zz.framework.web.entity.MvcMapping;
import cn.zz.framework.web.util.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * form表单装载到多个bean
 * 
 * @author admin
 *
 */
public class FormMealAdapt implements WebParamsAdapt {

	public Object[] doAdapt(MvcMapping mapping, HttpServletRequest request, HttpServletResponse response,
							HttpSession session) {
		if (StringUtil.isNullOrEmpty(mapping.getParamTypes())) {
			return null;
		}
		Object[] params = new Object[mapping.getParamTypes().size()];
		for (int i = 0; i < mapping.getParamTypes().size(); i++) {
			BeanEntity beanEntity = mapping.getParamTypes().get(i);
			if (beanEntity.getFieldType().isAssignableFrom(request.getClass())) {
				params[i] = request;
				continue;
			}
			if (beanEntity.getFieldType().isAssignableFrom(response.getClass())) {
				params[i] = response;
				continue;
			}
			if (beanEntity.getFieldType().isAssignableFrom(session.getClass())) {
				params[i] = session;
				continue;
			}
			if (BaseModel.class.isAssignableFrom(beanEntity.getFieldType())) {
				params[i] = RequestUtil.getBeanAll(request, null, beanEntity.getFieldType(),true);
				continue;
			}
			if (beanEntity.getFieldType().isPrimitive()|| InsideTypeConstant.INSIDE_TYPES.contains(beanEntity.getFieldType())) {
				ParamName paramNameAnnotion=beanEntity.getFieldType().getAnnotation(ParamName.class);
				String paraName=beanEntity.getFieldName();
				if(paramNameAnnotion!=null){
					paraName=paramNameAnnotion.value();
				}
				params[i]= PropertUtil.parseValue(request.getParameter(paraName), beanEntity.getFieldType());
				continue;
			}
		}
		return params;
	}

}
