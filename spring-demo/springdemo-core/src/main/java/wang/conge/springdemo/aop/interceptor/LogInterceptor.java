package wang.conge.springdemo.aop.interceptor;

import java.util.Optional;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class LogInterceptor implements MethodInterceptor{

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object ret = null;
		try {
			System.out.println("=====SpringAopAdvisor-调用前before逻辑===");
			
			ret = invocation.proceed();
			
			System.out.println("=====SpringAopAdvisor-调用后after逻辑===");
			
			return ret;
		} catch (Exception e) {
			System.out.println("=====SpringAopAdvisor-调用异常throw逻辑===");
			return Optional.empty();
		} finally {
			System.out.println("=====SpringAopAdvisor-调用最终结束逻辑===");
		}
	}

}
