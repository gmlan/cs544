package business.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAdvice {

	final static Logger logger = Logger.getLogger(ControllerAdvice.class);
	@After("execution(* presentation.controller.*.*(..))")
	public void afterController(JoinPoint joinPoint) {
		logger.info("Accessing " + joinPoint.getSignature().getName());
	}
}
