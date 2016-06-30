//package wang.conge.springdemo.aop.service;
//
//
//import javax.annotation.Resource;
//
//import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
//import org.springframework.aop.framework.AopInfrastructureBean;
//import org.springframework.aop.framework.ProxyFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ClassUtils;
//
//@Component
//public class AopLogBeanPostProcessor extends AbstractAdvisingBeanPostProcessor {
//
//	private static final long serialVersionUID = - 2224090870757689065L;
//
//	@Resource
//	public void setFunctionAdvisor(CommonLogAdvisor commonLogAdvisor) {
//		this.advisor = commonLogAdvisor;
//	}
//
//
//	private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
//	
//	@Override
//	public Object postProcessAfterInitialization(Object bean, String beanName) {
//		if (bean instanceof AopInfrastructureBean) {
//			return bean;
//		}
//
//		if (isEligible(bean, beanName)) {
//			ProxyFactory proxyFactory = new ProxyFactory();
//			proxyFactory.copyFrom(this);
//			proxyFactory.setTarget(bean);
//			proxyFactory.addAdvisor(this.advisor);
//			proxyFactory.setProxyTargetClass(true);
//			return proxyFactory.getProxy(this.beanClassLoader);
//		}
//
//		return bean;
//	}
//}
