package wang.conge.springdemo.aop.interceptor;

import org.aopalliance.aop.Advice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;

@Configuration
public class InterceptorAppConfig {
	
//	@Bean
//	public HelloService targetOrderService(){
//		return new HelloService();
//	}
	
	@Bean
	public Advice logAdvice(){
		return new LogInterceptor();
	}
	
//	@Bean(name = "orderServiceProxy")
//	public ProxyFactoryBean initOrderService(){
//		ProxyFactoryBean orderServiceProxy = new ProxyFactoryBean();
//		
//		orderServiceProxy.addAdvice(logAdvice());
//		orderServiceProxy.setTarget(targetOrderService());
//		
//		return orderServiceProxy;
//	}
	
	@Bean(name = "orderServiceProxy2")
	public Object initOrderService2(){
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setTarget(new HelloService());
		proxyFactory.addAdvice(logAdvice());
		proxyFactory.setProxyTargetClass(true);
		
		return proxyFactory.getProxy(ClassUtils.getDefaultClassLoader());
	}
}