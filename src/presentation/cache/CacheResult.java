package presentation.cache;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CacheResult {

	private String addKey;
	private List<String> removeKeys = new ArrayList<>();
	private CacheLevel cacheLevel;
	private Method method;
	
	public String getAddKey() {
		return addKey;
	}
	public void setAddKey(String addKey) {
		this.addKey = addKey;
	}
	public List<String> getRemoveKeys() {
		return removeKeys;
	}
	public void setRemoveKeys(List<String> removeKeys) {
		this.removeKeys = removeKeys;
	}
	public CacheLevel getCacheLevel() {
		return cacheLevel;
	}
	public void setCacheLevel(CacheLevel cacheLevel) {
		this.cacheLevel = cacheLevel;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	
}
