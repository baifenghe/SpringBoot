package com.bfh;

import com.bfh.utils.MyProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties({MyProperties.class})
@SuppressWarnings("all")
public class AppApplicationTests {

	@Autowired
	private MyProperties myProperties;//这边是编译器的问题，代码可以正常运行

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void contextLoads() {
		logger.info("AppApplicationTests.contextLoads()：" + myProperties.getRandomStr());
	}


}
