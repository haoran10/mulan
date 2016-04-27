package wang.conge.springdemo.core.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import wang.conge.springdemo.core.service.HelloBabyService;

public class AppStart {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppCoreConfig.class);
		
		HelloBabyService helloBabyService =  applicationContext.getBean(HelloBabyService.class);
		
		System.out.println(helloBabyService.say("kaka"));
	}

}
