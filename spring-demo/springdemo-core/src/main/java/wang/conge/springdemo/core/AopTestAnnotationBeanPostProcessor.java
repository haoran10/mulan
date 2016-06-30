package wang.conge.springdemo.core;
//package wang.conge.springdemo.aop.service;
//
//import java.lang.reflect.Method;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.stereotype.Component;
//
//import wang.conge.springdemo.aop.annotation.AopTestAnnotation;
//
//@Component
//public class AopTestAnnotationBeanPostProcessor implements BeanPostProcessor {
//
//	@Override
//	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//		Method[] methods = bean.getClass().getDeclaredMethods();
//		if (null == methods || 0 == methods.length) {
//			return bean;
//		}
//		
//		for(Method method:methods){
//			if(method.isAnnotationPresent(AopTestAnnotation.class)) {
//				System.out.println("===="+method.getName() + "==将要被AOP拦截");
//			}
//		}
//		
//		return bean;
//	}
//
//	@Override
//	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//		return bean;
//	}
//
//}
