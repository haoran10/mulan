package wang.conge.springdemo.core.service;

import org.springframework.stereotype.Component;

@Component
public class HelloBabyService {
	public String say(String name){
		return "hello:" + name;
	}
}
