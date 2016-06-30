package wang.conge.javasedemo.core.proxy;

import java.lang.reflect.Proxy;

public class JavaProxyTest {
	public static void main(String[] args) {
		HelloInvocationHandler invocationHandler = new HelloInvocationHandler(new HelloServiceImpl());

		HelloService helloService = (HelloService) Proxy.newProxyInstance(HelloService.class.getClassLoader(),
				new Class[] { HelloService.class }, invocationHandler);

		System.out.println(helloService.sayHello("conge"));
	}
}
