package wang.conge.springdemo.transaction.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DBCommonConfig {
	
	@Bean
	public PlatformTransactionManager transactionManager(DataSource datasource){
		return new DataSourceTransactionManager(datasource);
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource datasource){
		return new JdbcTemplate(datasource);
	}
	
}
