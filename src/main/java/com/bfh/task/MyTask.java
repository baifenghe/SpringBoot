package com.bfh.task;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * Author: bfh
 * Date: 2017/12/7
 */
@Configuration
@EnableScheduling
public class MyTask{

	/**
	 * 每2s打印一次
	 * "0 0/1 * * * *"  每一分钟打印一次
	 */
//	@Scheduled(cron = "0/2 * * * * *")
//	public void task1() {
//		System.out.println("MyTask.task1()," + new Date());
//	}
}
