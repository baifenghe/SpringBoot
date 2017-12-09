package com.bfh;

import com.bfh.controller.IndexController;
import com.bfh.utils.MyProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
//@EnableConfigurationProperties({MyProperties.class})
@SuppressWarnings("all")
public class AppApplicationTests {

	//@Autowired
	//private MyProperties myProperties;//这边是编译器的问题，代码可以正常运行

	private MockMvc mvc;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(new IndexController()).build();
	}

	@Test
	public void getHello() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

//	@Test
//	public void contextLoads() {
//		logger.info("AppApplicationTests.contextLoads()：" + myProperties.getRandomStr());
//	}


}
