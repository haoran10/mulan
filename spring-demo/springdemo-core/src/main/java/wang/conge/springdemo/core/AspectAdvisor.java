package wang.conge.springdemo.core;
//package wang.conge.springdemo.aop.service;
//
//import java.util.Optional;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class AspectAdvisor {
//
//	@Around("@annotation(wang.conge.springdemo.aop.annotation.AopTestAnnotation)")
//	public Object doInterceptor(ProceedingJoinPoint pjp) throws Throwable {
//		// TODO before
//		System.out.println("=====AspectAdvisor-调用前before逻辑===");
//
//		Object ret = null;
//		try {
//			ret = pjp.proceed();
//
//			// TODO after
//			System.out.println("=====AspectAdvisor-调用后after逻辑===");
//
//			return ret;
//		} catch (Exception e) {
//			// TODO throw
//			System.out.println("=====AspectAdvisor-调用异常throw逻辑===");
//			return Optional.empty();
//		} finally {
//			// TODO finally
//			System.out.println("=====AspectAdvisor-调用最终结束逻辑===");
//		}
//	}
//}
