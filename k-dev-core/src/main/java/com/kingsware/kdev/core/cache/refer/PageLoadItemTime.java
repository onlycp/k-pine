package com.kingsware.kdev.core.cache.refer;

import lombok.Data;

/**
 * @author chenp
 * @date 2024/5/11
 */
@Data
public class PageLoadItemTime {
    private long start;
    private long end;
    private String url;
}
