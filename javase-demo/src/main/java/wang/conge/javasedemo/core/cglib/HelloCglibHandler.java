package wang.conge.javasedemo.core.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class HelloCglibHandler implements MethodInterceptor {
	private Object delegate; 
	
	public HelloCglibHandler(Object delegate){
		this.delegate = delegate;
	}
	
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("intercept");
		return method.invoke(delegate, args);
	}

}
