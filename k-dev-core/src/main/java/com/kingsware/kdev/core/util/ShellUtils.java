package com.kingsware.kdev.core.util;

import com.kingsware.kdev.core.bean.NullOutputStream;
import com.kingsware.kdev.core.bean.ShellResult;
import com.kingsware.kdev.core.context.SpringContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.*;

import java.io.*;

/**
 * shell工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/4/6 5:18 下午
 */
@Slf4j
public class ShellUtils {

    /**
     * 执行shell命令
     * @param shell shell
     * @return  执行结果
     */
    public static ShellResult execute(String path, String shell, boolean waitForResult)  {
        try {
            if (shell.isEmpty()) {
                return ShellResult.fail("命令为空");
            }
            File workDir = resolveShellWorkDir(path);
            // 组装命令
            Process process = Runtime.getRuntime().exec(shell, null, workDir);
//            process.waitFor(3, TimeUnit.SECONDS);
            if (waitForResult) {
                InputStreamReader isr = new InputStreamReader(process.getInputStream());
                // 用缓冲器读行
                BufferedReader br = new BufferedReader(isr);
                String line;
                StringBuffer result = new StringBuffer();
                // 直到读完为止
                while ((line = br.readLine()) != null) {

                    result.append(line);
                }
                System.out.println("cmd:" + shell + "==>" + result.toString());
                return ShellResult.success(result.toString());
            }
            else {
                return ShellResult.success("命令已执行");
            }
        }
        catch (IOException e) {
            return ShellResult.fail(e.getMessage());
        }

    }

    public static void execAsync(String path, String command)  {
        try {
            File workDir = resolveShellWorkDir(path);
            CommandLine commandLine = CommandLine.parse(command);
            DefaultExecutor exec = new DefaultExecutor();
            PumpStreamHandler streamHandler = new PumpStreamHandler(new NullOutputStream(), new NullOutputStream());
            exec.setStreamHandler(streamHandler);
            exec.setWorkingDirectory(workDir);
            exec.execute(commandLine);
            // 避免主线程退出导致程序退出
            Thread.currentThread().join();
        }
        catch (Exception e) {
            log.info("error", e);
        }
    }

    private static File resolveShellWorkDir(String path) throws IOException {
        File baseRoot = PathSecurityUtils.canonicalFile(SpringContext.getProperties("file.base-path", "."), "file.base-path");
        File workDir = PathSecurityUtils.canonicalFile(path, "shell.path");
        if (!PathSecurityUtils.isInsideRoot(workDir, baseRoot)) {
            throw new IOException("shell path outside base root");
        }
        return workDir;
    }



}
