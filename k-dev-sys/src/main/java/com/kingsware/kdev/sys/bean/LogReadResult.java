package com.kingsware.kdev.sys.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogReadResult {
    private List<String> list;
    private long total;
    private long start;
    private long end;
}
