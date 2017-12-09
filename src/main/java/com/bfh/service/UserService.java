package com.bfh.service;

import com.bfh.entity.User;
import com.bfh.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: bfh
 * Date: 2017/12/7
 */
@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	public void save(User user){
		userMapper.save(user);
	}
}
