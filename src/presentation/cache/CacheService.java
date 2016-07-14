package presentation.cache;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import business.aop.ControllerAdvice;

public final class CacheService {

	final static Logger logger = Logger.getLogger(ControllerAdvice.class);
	
	private CacheService() {
	}
	
	public static void clearCache(HttpServletRequest request, CacheLevel level, String key){
		if(!key.isEmpty()){
			saveToCache(request, level, key, null);
		}
	}
	
	public static <T> T queryCache(HttpServletRequest request, CacheLevel level, String key){
		if(!key.isEmpty()){
			return readFromCache(request, level, key);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T execute(HttpServletRequest request, Object caller, String methodName){
		return (T) execute(request, caller, methodName, new Object[0]);
	}

	@SuppressWarnings("unchecked")
	public static <T> T execute(HttpServletRequest request, Object caller, String methodName,Object[] params) {
		T objInCache = null;
		try {
			CacheResult cacheResult = parseMethod(caller, methodName, params);
			//remove if set
			if(cacheResult.getRemoveKeys().size() != 0){
				for(String key : cacheResult.getRemoveKeys()){
					if(!key.isEmpty()){
						saveToCache(request, cacheResult.getCacheLevel(), key, null);
					}
				}
			}
			
			if(cacheResult.getAddKey().isEmpty()){//no cache
				objInCache = (T) cacheResult.getMethod().invoke(caller, params);
			}
			else{
				//read and add
				objInCache = readFromCache(request, cacheResult.getCacheLevel(), cacheResult.getAddKey());
				if (objInCache == null) {
					objInCache = (T) cacheResult.getMethod().invoke(caller, params);
					if (objInCache != null) {
						saveToCache(request, cacheResult.getCacheLevel(), cacheResult.getAddKey(), objInCache);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return objInCache;
	}

	@SuppressWarnings("unchecked")
	private static <T> T readFromCache(HttpServletRequest request, CacheLevel level, String key) {
		T result = null;
		if(!key.isEmpty()){
			if (level == CacheLevel.Application) {
				result = (T)request.getServletContext().getAttribute(key);
			} else if (level == CacheLevel.Session) {
				result = (T)request.getSession().getAttribute(key);
			}
		}
		return result;
	}

	private static <T> void saveToCache(HttpServletRequest request, CacheLevel level, String key, T obj) {
		if(!key.isEmpty()){
			if (level == CacheLevel.Application) {
				request.getServletContext().setAttribute(key, obj);
			} else if (level == CacheLevel.Session) {
				request.getSession().setAttribute(key, obj);
			}
		}
	}

	private static CacheResult parseMethod(Object caller, String methodName, Object[] params)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		CacheResult result = new CacheResult();
		for (Method method : caller.getClass().getMethods()) {
			if (!method.getName().equals(methodName)) {
				continue;
			}
			Class<?>[] parameterTypes = method.getParameterTypes();
			boolean matches = parameterTypes.length == params.length;
			for (int i = 0; matches && i < parameterTypes.length; i++) {
				if (!parameterTypes[i].isAssignableFrom(params[i].getClass())) {
					matches = false;
				}
			}
			if (matches) {				
				CacheSettings settings = method.getAnnotation(CacheSettings.class);
				if(settings != null){
					result.setCacheLevel(settings.cacheLevel());
					result.setAddKey(parseKey(params, settings.addKey()));
					result.setRemoveKeys(parseKeys(params, settings.removeKeys()));
				}				
				result.setMethod(method); 
				break;
			}
		}
		return result;
	}

	public static String parseKey(Object[] params, String key) {
		String result = key;
		for(int i = 0; i < params.length; i++){
			String placeHolder = "{" + i + "}";
			if(result.contains(placeHolder)){
				result = result.replace(placeHolder, params[i].toString());
			}				
		}		
		return result;
	}	
	
	private static List<String> parseKeys(Object[] params, String[] keys) {
		List<String> result = new ArrayList<>();
		for(String key : keys){
			if(!key.isEmpty()){
				result.add(parseKey(params, key));
			}
		}		
		return result;
	}	
}
