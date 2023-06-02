package com.kingsware.kdev.core.bean;

import lombok.Data;

import java.util.Map;
import java.util.Objects;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/6/2 10:20
 */
@Data
public class JsonPathSearchResult {

    private String path;
    private Map<String, Object> value;
    private String field;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JsonPathSearchResult that = (JsonPathSearchResult) o;
        return Objects.equals(path, that.path) && Objects.equals(field, that.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, field);
    }
}
