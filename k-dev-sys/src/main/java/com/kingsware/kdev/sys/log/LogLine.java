package com.kingsware.kdev.sys.log;

import lombok.Data;


/**
 * Represents a single line of log information.
 * This class encapsulates two pieces of information: the offset of the log line and the text content of the log line.
 * The offset is used to indicate the position of the log line in the log file.
 * The lineText is used to record the specific content of the log line.
 */
@Data
public class LogLine {
    /**
     * The offset of the log line.
     * Used to uniquely identify the position of a log line in the log file.
     */
    private Long offset;

    /**
     * The text content of the log line.
     * Used to store the actual content of the log.
     */
    private String lineText;
}
