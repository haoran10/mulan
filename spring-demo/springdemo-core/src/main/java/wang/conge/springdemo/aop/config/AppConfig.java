package wang.conge.springdemo.aop.config;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
//自动注解
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan("wang.conge.springdemo.aop.service")
public class AppConfig {
	
	@Bean(name = "orderServiceProxy")
	public ProxyFactoryBean initOrderService(String []proxyInterfaces , String[]interceptorNames,Object target){
		return null;
	}
}

