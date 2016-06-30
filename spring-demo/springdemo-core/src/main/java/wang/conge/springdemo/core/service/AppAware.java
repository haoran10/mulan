package wang.conge.springdemo.core.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class AppAware implements ApplicationContextAware{
	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		
		doSth();
	}
	
	public void doSth(){
		System.out.println(applicationContext.getApplicationName());
	}

}
