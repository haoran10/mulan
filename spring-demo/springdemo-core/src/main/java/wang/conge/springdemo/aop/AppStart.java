package wang.conge.springdemo.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import wang.conge.springdemo.aop.config.AppConfig;
import wang.conge.springdemo.aop.service.UserService;

public class AppStart {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		
		UserService myService = applicationContext.getBean(UserService.class);
		myService.addCar();
		
		myService.createOrder();
		
		myService.pay();
	}

}
