package wang.conge.springdemo.aop.service;

import java.lang.reflect.Method;
import java.util.Optional;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.stereotype.Component;

import wang.conge.springdemo.aop.annotation.AopLogAnnotation;

@Component
public class CommonLogAdvisor extends AbstractPointcutAdvisor{
	private static final long serialVersionUID = 1L;
	
	@Override
	public Pointcut getPointcut() {
		return new StaticMethodMatcherPointcut() {
			
			@Override
			public boolean matches(Method method, Class<?> targetClass) {
				//TODO matches targetClass 
				//if(targetClass matches AopTargetClass) return true;
				
				//TODO matches method 
				//if(method matches AopMethod) return true;
				
				if(method.isAnnotationPresent(AopLogAnnotation.class)) {
					return true;
				}
				
				return false;
			}
			
		};
	}

	@Override
	public Advice getAdvice() {
		return new MethodInterceptor() {
			@Override
			public Object invoke(MethodInvocation invocation) throws Throwable {
				Object ret = null;
				try {
					//TODO before
					System.out.println("=====SpringAopAdvisor-调用前before逻辑===");
					
					ret = invocation.proceed();
					
					//TODO after
					System.out.println("=====SpringAopAdvisor-调用后after逻辑===");
					
					return ret;
				} catch (Exception e) {
					//TODO throw
					System.out.println("=====SpringAopAdvisor-调用异常throw逻辑===");
					return Optional.empty();
				} finally {
					//TODO finally
					System.out.println("=====SpringAopAdvisor-调用最终结束逻辑===");
				}
			}
		};
	}
	
}
