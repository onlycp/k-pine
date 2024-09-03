package com.kingsware.kdev.core.kflow.bean;

import lombok.Data;

/**
 * A generic class for operation results.
 * Used to encapsulate the status, error information, and data related to an operation.
 *
 * @param <T> The type of the operation data.
 */
@Data
public class KOperationRet {
    // The status code of the operation, e.g., 0 for success, non-zero for failure.
    private int status;

    // Error message, describes the error in human-readable format when the operation fails.
    private java.lang.String errorMsg;

    // Stack details, provides detailed information about the error stack when the operation fails.
    private java.lang.String stackDetail;

    // Operation data, holds the data returned by a successful operation.
    private String data;
}
