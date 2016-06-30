package wang.conge.springdemo.aop.service;

import org.springframework.stereotype.Component;

import wang.conge.springdemo.aop.annotation.AopLogAnnotation;

@Component
public class UserService {
	
	@AopLogAnnotation
	public void addCar() {
		System.out.println("加入购物车");
	}
	
	@AopLogAnnotation
	public void createOrder() {
		System.out.println("下单");
	}
	
	public void pay() {
		System.out.println("付款");
	}
}
