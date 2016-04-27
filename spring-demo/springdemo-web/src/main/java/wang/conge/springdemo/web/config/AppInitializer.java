package wang.conge.springdemo.web.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

/**
 * app start
 * @author haozhan
 * 2016年4月26日
 */
public class AppInitializer implements WebApplicationInitializer {
	private final String DEFAULT_MAPPING = "/";
	private final Integer LOAD_ON_STARTUP = 1;
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(AppCoreConfig.class);
		applicationContext.register(WebConfig.class);
		applicationContext.setServletContext(servletContext);
		
		servletContext.addListener(new ContextLoaderListener(applicationContext));
		Dynamic dynamic = servletContext.addServlet(AbstractDispatcherServletInitializer.DEFAULT_SERVLET_NAME, new DispatcherServlet(applicationContext));
		dynamic.addMapping(DEFAULT_MAPPING);
		dynamic.setLoadOnStartup(LOAD_ON_STARTUP);
	}

}
