package wang.conge.springdemo.transaction.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserDao {
	@Autowired JdbcTemplate jdbcTemplate;
	
	private final String insert_sql = "INSERT INTO users(id,name,email) VALUES (?, ?,?)";
	
	private final String query_sql = "select id,name,email from users";
	
	public void testInsert(int id,String name,String email){
		jdbcTemplate.update("update users set name = ? where id = ?","kakanew",1);//这句一定正常执行
		
		System.out.println("执行正常SQL后结果：===");
		queryAll();
		
		jdbcTemplate.update(insert_sql, id,name,email);//有可能因为主键冲突异常
	}
	
	public void queryAll(){
		List<Map<String, Object>> list = jdbcTemplate.queryForList(query_sql);
		
		for(Map<String, Object> map:list){
			System.out.println(map);
		}
	}
}
