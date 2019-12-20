package cn.zz.framework.web.adapt.iface;

import cn.zz.framework.web.entity.MvcMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * 参数适配器
 * @author admin
 *
 */
public interface WebParamsAdapt {

	 Object[] doAdapt(MvcMapping mapping, HttpServletRequest request, HttpServletResponse response, HttpSession session);

}
