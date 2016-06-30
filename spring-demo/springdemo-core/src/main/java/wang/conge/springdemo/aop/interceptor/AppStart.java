package wang.conge.springdemo.aop.interceptor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppStart {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(InterceptorAppConfig.class);
		
		HelloService myService = (HelloService) applicationContext.getBean(HelloService.class);
		
		myService.sayHello("CONGE");
	}

}
