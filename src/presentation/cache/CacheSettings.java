package presentation.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheSettings {	
	String addKey() default "";
	String[] removeKeys() default "";
	CacheLevel cacheLevel() default CacheLevel.Session;
}
