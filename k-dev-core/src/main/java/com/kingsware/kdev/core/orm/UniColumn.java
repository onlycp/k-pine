package com.kingsware.kdev.core.orm;

/**
 * 唯一列定义
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/27 4:28 下午
 */
public class UniColumn {

    private String[] key;
    private String errorMessage;

    public String[] getKey() {
        return key;
    }

    public void setKey(String[] key) {
        this.key = key;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    /**
     * 构建逻辑删除
     * @param key           列
     * @param errorMessage  提示信息
     * @return              唯一列
     */
    public UniColumn build(String[] key, String errorMessage) {
        UniColumn uc = new UniColumn();
        uc.setKey(key);
        uc.setErrorMessage(errorMessage);
        return uc;
    }
}
