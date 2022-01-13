package com.kingsware.kdev;

import com.kingsware.kdev.biz.kw.service.KwQueueTaskService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class LcdApplicationTests {
	@Resource
	KwQueueTaskService kwQueueTaskService;

	@Test
	void contextLoads() {
		//kwQueueTaskService
	}

}
