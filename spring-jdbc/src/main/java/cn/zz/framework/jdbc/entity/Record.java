package cn.zz.framework.jdbc.entity;

import cn.zz.framework.core.util.PropertUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author zenghzong
 * @Since 2019/8/28
 * @Version 1.0
 */
public class Record implements Map<String, Object> {
	private Map<String, Object> map = new HashMap<String, Object>();

	public Record() {
	}

	public Record(Map<String, Object> map) {
		if (map == null) {
			return;
		}
		this.map = map;
	}

	public int size() {
		return map.size();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public Object get(Object key) {
		return map.get(key);
	}

	public Object put(String key, Object value) {
		return map.put(key,value);
	}

	public Object remove(Object key) {
		return map.remove(key);
	}

	public void putAll(Map<? extends String, ?> m) {
		map.putAll(m);
	}

	public void clear() {
		map.clear();
	}

	public Set<String> keySet() {
		return map.keySet();
	}

	public Collection<Object> values() {
		return map.values();
	}

	public Set<Entry<String, Object>> entrySet() {
		return map.entrySet();
	}
	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public Object parsBean(Class<?> cla) {
		return PropertUtil.mapToModel(map,cla);
	}

}