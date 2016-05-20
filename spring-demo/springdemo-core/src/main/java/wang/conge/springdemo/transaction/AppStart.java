package wang.conge.springdemo.transaction;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import wang.conge.springdemo.transaction.config.AppConfig;
import wang.conge.springdemo.transaction.service.UserDao;

public class AppStart {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

		UserDao myService = applicationContext.getBean(UserDao.class);

		try {
			System.out.println("执行SQL前结果：===");
			myService.queryAll();

			myService.testInsert(2, "wow", "wow@163.com");// 主键冲突，数据回滚
		} catch (Exception e) {
			System.out.println(e);
		}

		System.out.println("异常回滚SQL后结果：===");
		myService.queryAll();
	}

}
