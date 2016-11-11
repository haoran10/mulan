package wang.conge.springdemo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
	DataSourceConfig.class,
	MyBatisConfig.class,
})
@ComponentScan(AppConfigConstant.SERVICE_PACKAGE)
public class AppCoreConfig {

}
