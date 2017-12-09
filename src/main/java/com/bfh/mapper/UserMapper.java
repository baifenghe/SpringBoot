package com.bfh.mapper;

import com.bfh.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Author: bfh
 * Date: 2017/12/7
 */
@Mapper
public interface UserMapper {

	void save(User user);
}
