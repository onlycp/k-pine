package com.kingsware.kdev.core.plugins.file;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.encrypt.EncryptProperties;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.util.AESUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * 文件AES加密
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/2/21 08:57
 */
@Component
@Slf4j
public class AesFileEncryptPlugin implements FileEncryptPlugin{

    @PostConstruct
    public void info() {
       log.info("文件加密插件加载完成:{}", name());
    }

    @Override
    public File encrypt(File file) {
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            EncryptProperties encryptProperties = SpringContext.getBean(EncryptProperties.class);
            byte[] afterBytes = AESUtil.encrypt(new String(bytes, StandardCharsets.UTF_8), encryptProperties.getAes().getSecret()).getBytes(StandardCharsets.UTF_8);
            // 创建对应目录
            String distFolder = file.getParent() + File.separator + name();
            File folderFile = new File(distFolder);
            if (!folderFile.exists()) {
                folderFile.mkdirs();
            }
            // 新的文件路径
            String targetFilePath = distFolder + File.separator + file.getName();

            // 删除旧文件
            Files.deleteIfExists(new File(targetFilePath).toPath());
            // 写入新文件
            Files.write(new File(targetFilePath).toPath(), afterBytes);
            // 返回
            return new File(targetFilePath);
        } catch (IOException e) {
            throw new BusinessException(e, null);
        }

    }

    @Override
    public String name() {
        return "aes";
    }
}
