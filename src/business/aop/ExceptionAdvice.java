package business.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import business.exceptions.BackendException;

@Aspect
@Component
public class ExceptionAdvice {
	final static Logger logger = Logger.getLogger(ControllerAdvice.class);

	@AfterThrowing(pointcut="execution(* business.*.*(..))", throwing="exception")
	public void afterThrowing(JoinPoint joinpoint,  BackendException exception) {
		logger.debug(joinpoint.getSignature().getName() + " has thrown an exception " + exception.getMessage());
	}
}
