package com.bfh.controller;

import com.bfh.entity.User;
import com.bfh.service.UserService;
import com.bfh.task.Task1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * IndexController
 * Author: bfh
 * Date: 2017/12/3
 */
@RestController
public class IndexController {

	private Logger logger =  LoggerFactory.getLogger(this.getClass());
	@Autowired
	private Task1 task1;
	@Autowired
	private UserService userService;



	//hello?name=jay&state=19
	@RequestMapping("/hello")
	public String hello(String name, int state){
		return"name "+name+"---"+state;
	}

	@RequestMapping("/")
	public String index() {
		logger.info("index");
		return "index";
	}

	@RequestMapping("/task1")
	public String task1() throws Exception {
		task1.doTaskOne();
		task1.doTaskTwo();
		task1.doTaskThree();

		return "task1";
	}


	@RequestMapping("/save")
	public @ResponseBody
	User save() {

		User user = new User();
		user.setId(null);
		user.setUserName("张三");
		user.setPassword("123");

		userService.save(user);
		return user;
	}
}
