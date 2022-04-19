package com.kingsware.kdev.core.bean;

import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/4/19 2:43 PM
 */
public class NullOutputStream extends OutputStream {
    @Override
    public void write(int b) throws IOException {

    }
}
