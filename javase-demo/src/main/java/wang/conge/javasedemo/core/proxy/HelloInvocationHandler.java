package wang.conge.javasedemo.core.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HelloInvocationHandler implements InvocationHandler{
	private Object delegate; 
	
	public HelloInvocationHandler(Object delegate){
		this.delegate = delegate;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("invoke");
		return method.invoke(this.delegate, args);
	}

}
