package com.kingsware.kdev.sys.argv;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.PipedOutputStream;

/**
 * The StreamingLog class is used for streaming log transmission.
 * It encapsulates a PipedOutputStream object and a client identifier.
 *
 * The PipedOutputStream is used to create a pipeline output stream, which can be connected to a pipeline input stream,
 * allowing logs to be transmitted in a streaming manner.
 *
 * The client identifier is used to identify the client to which the log belongs, ensuring that logs are correctly routed and processed.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StreamingLog {

    /**
     * PipedOutputStream is used to create a pipeline output stream for log transmission.
     * It can be connected to a pipeline input stream to achieve streaming transmission of logs.
     */
    private PipedOutputStream pipedOutputStream;


    /**
     * The client identifier is used to identify the client to which the log belongs.
     * This allows logs from different clients to be distinguished and processed separately.
     */
    private String clientId;
}
