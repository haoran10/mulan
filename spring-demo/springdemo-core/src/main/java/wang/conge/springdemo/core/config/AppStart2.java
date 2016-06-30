//package wang.conge.springdemo.core.config;
//
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.xml.XmlBeanFactory;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//
//import wang.conge.springdemo.core.service.HelloBabyService;
//
//public class AppStart2 {
//	
//	@SuppressWarnings("deprecation")
//	public static void main(String[] args) {
//		Resource resource = new ClassPathResource("app.xml");
//		BeanFactory beanFactory = new XmlBeanFactory(resource );
//		
//		HelloBabyService helloBabyService =  beanFactory.getBean(HelloBabyService.class);
//		
//		System.out.println(helloBabyService.say("kaka"));
//	}
//
//}
