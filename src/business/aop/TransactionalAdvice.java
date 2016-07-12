package business.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;

public class TransactionalAdvice {

	final static Logger logger = Logger.getLogger(ControllerAdvice.class);

	@After("@annotation(org.springframework.transaction.annotation.Transactional)")
	public void afterTransactional(JoinPoint joinPoint) {
		logger.info("Accessing transactional method " + joinPoint.getSignature().getName());
	}
}
