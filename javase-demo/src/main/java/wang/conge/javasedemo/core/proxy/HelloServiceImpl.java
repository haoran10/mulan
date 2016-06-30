package wang.conge.javasedemo.core.proxy;

public class HelloServiceImpl implements HelloService {

	@Override
	public String sayHello(String name) {
		return "hello:" + name;
	}

}
