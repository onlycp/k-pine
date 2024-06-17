package com.kingsware.kdev.core.util;

/**
 * @author chenp
 * @date 2024/6/14
 */
import java.io.IOException;
import java.io.InputStream;

public class NullInputStream extends InputStream {

    @Override
    public int read() throws IOException {
        return -1; // Indicates end of stream
    }

    @Override
    public int read(byte[] b) throws IOException {
        return -1; // Indicates end of stream
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return -1; // Indicates end of stream
    }

    @Override
    public long skip(long n) throws IOException {
        return 0; // Nothing to skip
    }

    @Override
    public int available() throws IOException {
        return 0; // No data available
    }

    @Override
    public void close() throws IOException {
        // Nothing to close
    }

    @Override
    public synchronized void mark(int readlimit) {
        // Mark is not supported
    }

    @Override
    public synchronized void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    public static void main(String[] args) {
        InputStream nullStream = new NullInputStream();

        try {
            System.out.println("Reading from NullInputStream: " + nullStream.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
