package wang.conge.springdemo.aop.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan("wang.conge.springdemo.aop.service")
public class AppConfig {
	
}