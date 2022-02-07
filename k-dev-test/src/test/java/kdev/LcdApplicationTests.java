package kdev;

import com.kingsware.kdev.biz.kw.enums.QueueTaskStatusEnum;
import com.kingsware.kdev.biz.kw.service.KwQueueTaskService;
import com.kingsware.kdev.biz.kw.service.KwWaterService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;

@SpringBootTest
class LcdApplicationTests {
	@Resource
	KwQueueTaskService kwQueueTaskService;

	@Resource
	KwWaterService kwWaterService;

//	@Test
//	void contextLoads() {
//		kwQueueTaskService.updateStatus("d7e03edbc3164d2ea563f2ad9c7279d7", QueueTaskStatusEnum.PROCESSING);
//	}

//	@Test
//	void waterTest() {
//		File file = new File("/Users/andyzheng/Desktop/标准流水(1).xlsx");
//		kwWaterService.excel2WaterDto(file);
//	}

}
