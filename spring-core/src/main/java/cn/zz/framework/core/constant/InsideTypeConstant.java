package cn.zz.framework.core.constant;

import cn.zz.framework.core.util.AntUtil;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @Author zenghzong
 * @Since 2019/8/23
 * @Version 1.0
 */
public class InsideTypeConstant {

	public static final List<Class<?>> INSIDE_TYPES=new ArrayList<Class<?>>(Arrays.asList(
			new Class<?>[]{
				String.class,Integer.class,Double.class,Float.class,Boolean.class,Date.class
				}));
	public static final List<String> SYSTEM_MATEHERS = new ArrayList<String>(
			Arrays.asList(new String[] { "java.*", "javax.*" }));

	public static final boolean isSystem(Class<?> clazz) {
		for(String mateher:SYSTEM_MATEHERS){
			if(AntUtil.isAntMatch(clazz.getName(), mateher)){
				return true;
			}
		}
		return false;
	}
}
