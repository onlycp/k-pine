package com.kingsware.kdev.sys.argv;

import lombok.Data;

/**
 * The LogTailArgv class is used to encapsulate the parameters for log tail queries.
 * It leverages the @Data annotation from the Lombok library to automatically generate getters and setters,
 * simplifying the process of handling log query parameters.
 */
@Data
public class LogTailArgv {

    /**
     * The application name, used to specify which application's logs to query.
     */
    private String app;

    /**
     * The log level, used to filter logs by level.
     */
    private String level;

    /**
     * The keyword for log filtering, used to search for logs containing the specified keyword.
     */
    private String keyword;

    /**
     * The offset value, used to specify the starting point of the log query.
     */
    private Long offset;

    /**
     * The limit value, used to specify the maximum number of logs to return.
     */
    private Long limit;

    /**
     * The client ID, used to identify the client making the log query.
     */
    private String clientId;
}
