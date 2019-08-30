package cn.zz.framework.core.entity;

import lombok.Data;

/**
 * @Author zenghzong
 * @Since 2019/8/23
 * @Version 1.0
 */
@Data
public class AspectPoint extends BaseModel{

	private AspectAbler abler;
	
	private Object[] params;


	public AspectPoint(AspectAbler abler, Object[] params) {
		super();
		this.abler = abler;
		this.params = params;
	}

	public Object invoke() throws Throwable {
		return abler.invoke(this, params);
	}
	
}
