package com.kingsware.kdev.core.cron;

import com.kingsware.kdev.core.cache.api.ApiInfo;
import com.kingsware.kdev.core.cache.api.ApiManager;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.kflow.KflowProperties;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.FaasFailRecord;
import com.kingsware.kdev.core.util.FileUtils;
import com.kingsware.kdev.core.util.HttpUtil;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.NumberUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

/**
 * Faas失败日志失败
 * @author chenp
 * @date 2023/2/24
 */
@Slf4j
public class FaasFailTask implements KTask, KRunner {


    public FaasFailTask() {

    }

    /**
     * 定时拉取字典项
     */
    @Override
    public void execute() {
        // 获取失败序号
        String seqFile = "fail" + File.separator + "faas.seq";
        int seq = 0;
        if (new File(seqFile).exists()) {
            String text = FileUtils.readFileText(new File(seqFile)).trim();
            seq = Integer.parseInt(text);
        }
        // Faas失败文件
        String logFile = "fail" + File.separator + "faas.log";
        if (!new File(logFile).exists()) {
            return;
        }
        try {
            List<String> lines = Files.readAllLines(Paths.get(logFile));
            for (int i = seq; i < lines.size(); i++) {
                String line = lines.get(i);
                String recordStr = new String(Base64.getDecoder().decode(line), StandardCharsets.UTF_8);
                FaasFailRecord record = JsonUtil.toBean(recordStr, FaasFailRecord.class);
                try {
                    HttpUtil.callHttp(record.getUrl(), record.getBody(), record.getHeaderMap());
                    log.info("补发:{}", JsonUtil.toJson(record));
                    Files.write(Paths.get(seqFile), (i+"").getBytes(StandardCharsets.UTF_8));
                } catch (Exception e) {
                    // 如果失败即终止
                    return;
                }
            }
            // 删除文件
            if (seq == lines.size()-1) {
                Files.deleteIfExists(Paths.get(seqFile));
                Files.deleteIfExists(Paths.get(logFile));
            }

        }
        catch (Exception e) {
            log.info("FAAS失败日志读取失败");
        }


    }

    @Override
    public String cron() {
        return "0/10 * * * * ?";
    }

    @Override
    public String name() {
        return "FaasFailTask";
    }

    @Override
    public void runNow() {
        this.execute();
    }
}
