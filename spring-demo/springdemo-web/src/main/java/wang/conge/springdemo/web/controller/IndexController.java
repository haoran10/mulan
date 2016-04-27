package wang.conge.springdemo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import wang.conge.springdemo.web.dto.BaseDto;
import wang.conge.springdemo.web.dto.DtoUtil;

@Controller
@RequestMapping("/")
public class IndexController {

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public BaseDto<Void> index() {
		return DtoUtil.ok();
	}
	
	@ResponseBody
	@RequestMapping(path="/testJson",method = RequestMethod.GET)
	public BaseDto<Void> testJson() {
		return DtoUtil.ok();
	}
}
