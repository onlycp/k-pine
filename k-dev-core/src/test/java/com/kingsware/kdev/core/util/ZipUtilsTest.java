package com.kingsware.kdev.core.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.UUID;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

@EnabledOnOs({OS.MAC, OS.LINUX})
class ZipUtilsTest {

    @Test
    void unzipRejectsEntriesOutsideDestinationDirectory() throws Exception {
        Path tempRoot = Files.createTempDirectory("ziputils-test-");
        Path destDir = Files.createDirectory(tempRoot.resolve("dest"));
        Path zipFile = tempRoot.resolve("archive.zip");
        Path escapedFile = tempRoot.resolve("escaped.txt");

        try {
            createZip(zipFile, "../escaped.txt", "payload");

            assertThrows(RuntimeException.class,
                    () -> ZipUtils.unzip(destDir.toString(), zipFile.toString(), StandardCharsets.UTF_8.name()));
            assertFalse(Files.exists(escapedFile));
        } finally {
            deleteRecursively(tempRoot);
        }
    }

    @Test
    void unzipRestrictsExtractedDirectoryAndFilePermissions() throws Exception {
        Path tempRoot = Files.createTempDirectory("ziputils-perm-");
        Path destDir = Files.createDirectory(tempRoot.resolve("dest"));
        Path zipFile = tempRoot.resolve("archive.zip");

        try {
            createZip(zipFile, "nested/", "");
            appendZip(zipFile, "nested/script.sh", "echo hi");

            ZipUtils.unzip(destDir.toString(), zipFile.toString(), StandardCharsets.UTF_8.name());

            Path nestedDir = destDir.resolve("nested");
            Path extractedFile = nestedDir.resolve("script.sh");
            Set<PosixFilePermission> dirPermissions = Files.getPosixFilePermissions(nestedDir);
            assertFalse(dirPermissions.contains(PosixFilePermission.GROUP_EXECUTE));
            assertFalse(dirPermissions.contains(PosixFilePermission.OTHERS_EXECUTE));

            Set<PosixFilePermission> filePermissions = Files.getPosixFilePermissions(extractedFile);
            assertFalse(filePermissions.contains(PosixFilePermission.OWNER_EXECUTE));
            assertFalse(filePermissions.contains(PosixFilePermission.GROUP_EXECUTE));
            assertFalse(filePermissions.contains(PosixFilePermission.OTHERS_EXECUTE));
            assertTrue(Files.exists(extractedFile));
        } finally {
            deleteRecursively(tempRoot);
        }
    }

    private static void createZip(Path zipFile, String entryName, String content) throws IOException {
        try (OutputStream out = Files.newOutputStream(zipFile);
             ZipOutputStream zip = new ZipOutputStream(out, StandardCharsets.UTF_8)) {
            zip.putNextEntry(new ZipEntry(entryName));
            zip.write(content.getBytes(StandardCharsets.UTF_8));
            zip.closeEntry();
        }
    }

    private static void appendZip(Path zipFile, String entryName, String content) throws IOException {
        Path tempZip = Files.createTempFile("ziputils-append-", ".zip");
        try (java.util.zip.ZipFile existing = new java.util.zip.ZipFile(zipFile.toFile(), StandardCharsets.UTF_8);
             OutputStream out = Files.newOutputStream(tempZip);
             ZipOutputStream zip = new ZipOutputStream(out, StandardCharsets.UTF_8)) {
            existing.stream().forEach(entry -> {
                try {
                    zip.putNextEntry(new ZipEntry(entry.getName()));
                    if (!entry.isDirectory()) {
                        zip.write(existing.getInputStream(entry).readAllBytes());
                    }
                    zip.closeEntry();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            zip.putNextEntry(new ZipEntry(entryName));
            zip.write(content.getBytes(StandardCharsets.UTF_8));
            zip.closeEntry();
        }
        Files.move(tempZip, zipFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
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
                        throw new RuntimeException("cleanup failed: " + UUID.randomUUID(), e);
                    }
                });
    }
}
