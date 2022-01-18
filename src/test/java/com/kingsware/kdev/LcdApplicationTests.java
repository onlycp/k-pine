package com.kingsware.kdev;

import com.kingsware.kdev.biz.kw.enums.QueueTaskStatusEnum;
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
		kwQueueTaskService.updateStatus("d7e03edbc3164d2ea563f2ad9c7279d7", QueueTaskStatusEnum.PROCESSING);
	}

}
