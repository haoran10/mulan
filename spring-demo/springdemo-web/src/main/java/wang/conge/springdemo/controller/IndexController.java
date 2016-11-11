package wang.conge.springdemo.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import wang.conge.springdemo.dto.BaseDto;
import wang.conge.springdemo.dto.DtoUtil;
import wang.conge.springdemo.mapper.UserMapper;
import wang.conge.springdemo.pojo.UserDO;

@Controller
@RequestMapping("/")
public class IndexController {
	
	@Resource
	UserMapper userMapper;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public BaseDto<Void> index() {
		return DtoUtil.ok();
	}
	
	@ResponseBody
	@RequestMapping(path="/testJson",method = RequestMethod.GET)
	public BaseDto<UserDO> testJson() {
		return DtoUtil.ok(userMapper.getUser(1L));
	}
}
