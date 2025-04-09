package com.kingsware.kdev.sys.ret;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * LogTailRet类用于表示日志文件中的一段文本区域
 * 它主要封装了日志内容的开始位置、结束位置以及这段日志内容的行列表
 */
@Data
public class LogTailRet {

    /**
     * 表示日志内容的开始位置（以字节为单位）
     * 这个值标志着日志内容的起始点，有助于快速定位到日志文件中的具体位置
     */
    private Long begin;

    /**
     * 表示日志内容的结束位置（以字节为单位）
     * 结束位置与开始位置一起，定义了一段连续的日志内容范围
     */
    private Long offset;

    /**
     * 存储在这段日志内容范围内的所有行
     * 每一行代表日志中的一个记录，列表形式便于逐行处理和展示日志内容
     */
    private List<String> lines = new ArrayList<>();
}
