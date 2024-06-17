package com.kingsware.kdev.core.util;

import lombok.Cleanup;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

public class MultiThreadDownloader {

    private static final int MAX_THREAD_COUNT = 10; // Maximum number of threads
    private static final int CHUNK_SIZE = 10 * 1024 * 1024; // 10MB chunk size
    public static int getFileSize(String fileURL) throws Exception {
        URL url = new URL(fileURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("Charset", "UTF-8");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(60000);
        connection.setDoInput(true);
        @Cleanup InputStream is = connection.getInputStream();
        int contentLength = connection.getContentLength();
        connection.disconnect();
        return contentLength;
    }

    private static int calculateThreadCount(int fileSize) {
        if (fileSize <= CHUNK_SIZE) {
            return 1;
        } else {
            return Math.min(MAX_THREAD_COUNT, (fileSize + CHUNK_SIZE - 1) / CHUNK_SIZE);
        }
    }


    public static void downloadFile(String fileURL, String saveFilePath, int maxRetryCount) throws Exception {
        int contentLength = getFileSize(fileURL);
        int threadCount = calculateThreadCount(contentLength);
        RandomAccessFile file = new RandomAccessFile(saveFilePath, "rw");
        file.setLength(contentLength);
        file.close();

        CountDownLatch latch = new CountDownLatch(threadCount);
        int partSize = contentLength / threadCount;

        for (int i = 0; i < threadCount; i++) {
            int start = i * partSize;
            int end = (i == threadCount - 1) ? contentLength : start + partSize - 1;
            new Thread(new DownloadTask(fileURL, saveFilePath, start, end, latch, maxRetryCount)).start();
        }

        latch.await();
        System.out.println("Download completed!");
    }

    private static class DownloadTask implements Runnable {
        private String fileURL;
        private String saveFilePath;
        private int start;
        private int end;
        private CountDownLatch latch;
        private int maxRetryCount;

        public DownloadTask(String fileURL, String saveFilePath, int start, int end, CountDownLatch latch, int maxRetryCount) {
            this.fileURL = fileURL;
            this.saveFilePath = saveFilePath;
            this.start = start;
            this.end = end;
            this.latch = latch;
            this.maxRetryCount = maxRetryCount;
        }

        @Override
        public void run() {
            int attempt = 0;
            boolean success = false;

            while (attempt < maxRetryCount && !success) {
                try {
                    URL url = new URL(fileURL);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("Range", "bytes=" + start + "-" + end);
                    connection.setRequestProperty("connection", "Keep-Alive");
                    connection.setRequestProperty("Charset", "UTF-8");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(60000);
                    connection.setDoInput(true);
                    connection.connect();

                    InputStream inputStream = connection.getInputStream();
                    RandomAccessFile file = new RandomAccessFile(saveFilePath, "rw");
                    file.seek(start);

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        file.write(buffer, 0, bytesRead);
                    }

                    file.close();
                    inputStream.close();
                    connection.disconnect();

                    success = true; // If download succeeds, set success to true
                } catch (IOException e) {
                    attempt++;
                    System.out.println("Attempt " + attempt + " for part " + start + "-" + end + " failed.");
                    if (attempt == maxRetryCount) {
                        e.printStackTrace();
                    } else {
                        try {
                            Thread.sleep(1000); // Wait for 1 second before retrying
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
            latch.countDown();
        }
    }
}
