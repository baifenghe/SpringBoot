package com.bfh.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Author: bfh
 * Date: 2017/12/4
 */
@ConfigurationProperties(prefix = "com.bfh.values")
public class MyProperties {

	private String name;
	private String randomStr;
	private int randomNum;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRandomStr() {
		return randomStr;
	}

	public void setRandomStr(String randomStr) {
		this.randomStr = randomStr;
	}

	public int getRandomNum() {
		return randomNum;
	}

	public void setRandomNum(int randomNum) {
		this.randomNum = randomNum;
	}
}
