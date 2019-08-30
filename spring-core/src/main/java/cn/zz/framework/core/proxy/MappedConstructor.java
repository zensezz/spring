package cn.zz.framework.core.proxy;

import cn.zz.framework.core.entity.BaseModel;

import java.lang.reflect.Constructor;

public  class MappedConstructor extends BaseModel {

		private Constructor<?> constructor;

		private Class<?>[] types;

		private Object[] parameters;

		public Constructor<?> getConstructor() {
			return constructor;
		}

		public void setConstructor(Constructor<?> constructor) {
			this.constructor = constructor;
		}

		public Class<?>[] getTypes() {
			return types;
		}

		public void setTypes(Class<?>[] executable) {
			this.types = executable;
		}

		public Object[] getParameters() {
			return parameters;
		}

		public void setParameters(Object[] parameters) {
			this.parameters = parameters;
		}

	}
