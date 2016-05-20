package wang.conge.springdemo.transaction.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
	DataSourceConfig.class,
	DBCommonConfig.class
})
@ComponentScan("wang.conge.springdemo.transaction.service")
public class AppConfig {
	
}