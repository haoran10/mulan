package wang.conge.javasedemo.core.cglib;

import net.sf.cglib.proxy.Enhancer;
import wang.conge.javasedemo.core.proxy.HelloServiceImpl;

public class EnhancerTest {
	public static void main(String[] args) {
		HelloCglibHandler cglibHandler = new HelloCglibHandler(new HelloServiceImpl());

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloServiceImpl.class);
        enhancer.setCallback(cglibHandler);
        
        HelloServiceImpl helloService = (HelloServiceImpl) enhancer.create();
        
        System.out.println(helloService.sayHello("conge"));
    }
}