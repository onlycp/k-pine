package com.kingsware.kdev.sys.manager;

import com.kingsware.kdev.core.context.SpringContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.springframework.context.support.StaticApplicationContext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EnabledOnOs({OS.MAC, OS.LINUX})
class FileManagerTest {

    @Test
    void registerRestrictsUploadDirectoryAndFilePermissions() throws Exception {
        StaticApplicationContext context = new StaticApplicationContext();
        new SpringContext().setApplicationContext(context);

        String targetDir = "test-upload-" + UUID.randomUUID();
        String fileName = "script.sh";
        Path dirPath = Path.of("res", targetDir);
        Path filePath = dirPath.resolve(fileName);

        try {
            FileManager.getInstance().register(
                    new ByteArrayInputStream("echo hi".getBytes(StandardCharsets.UTF_8)),
                    fileName,
                    "echo hi".getBytes(StandardCharsets.UTF_8).length,
                    targetDir,
                    1,
                    "res",
                    true
            );

            Set<PosixFilePermission> dirPermissions = Files.getPosixFilePermissions(dirPath);
            assertFalse(dirPermissions.contains(PosixFilePermission.GROUP_EXECUTE));
            assertFalse(dirPermissions.contains(PosixFilePermission.OTHERS_EXECUTE));

            Set<PosixFilePermission> filePermissions = Files.getPosixFilePermissions(filePath);
            assertFalse(filePermissions.contains(PosixFilePermission.OWNER_EXECUTE));
            assertFalse(filePermissions.contains(PosixFilePermission.GROUP_EXECUTE));
            assertFalse(filePermissions.contains(PosixFilePermission.OTHERS_EXECUTE));
            assertTrue(Files.exists(filePath));
        } finally {
            deleteRecursively(dirPath);
            context.close();
        }
    }

    private static void deleteRecursively(Path path) throws IOException {
        if (!Files.exists(path)) {
            return;
        }
        Files.walk(path)
                .sorted((a, b) -> b.compareTo(a))
                .forEach(p -> {
                    try {
                        Files.deleteIfExists(p);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
