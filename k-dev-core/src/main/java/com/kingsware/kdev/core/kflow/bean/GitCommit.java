package com.kingsware.kdev.core.kflow.bean;

import com.kingsware.kdev.core.util.JsonUtil;
import lombok.Data;

/**
 * git日志
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2024/8/29 10:03
 */
@Data
public class GitCommit {

    // 作者名称，用于记录代码的创作或修改者
    private String author;

    // 时间戳，表示代码提交或修改的时间
    private String time;

    // 标签，用于对代码进行分类或标记特定版本
    private String tag;

    // 扩展仓库ID，用于关联代码与特定的版本库
    private String extendRepoId;

    // 扩展提交ID，唯一标识一次代码提交动作
    private String extendCommitId;

    // 提交信息，描述此次代码提交的背景、目的或变更内容
    private String message;

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }

    /**
     * 解析git提交信息的字符串表示形式，生成一个GitCommit对象
     *
     * @param str 字符串形式的git提交信息
     * @return 返回解析后的GitCommit对象
     */
    public static GitCommit parse(String str) {
        return JsonUtil.toBean(str, GitCommit.class);
    }
}

