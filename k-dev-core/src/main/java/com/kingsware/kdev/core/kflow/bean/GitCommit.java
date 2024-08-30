package com.kingsware.kdev.core.kflow.bean;

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
        StringBuffer sb = new StringBuffer();
        sb.append("author:").append(author).append("\n");
        sb.append("time:").append(time).append("\n");
        sb.append("tag:").append(tag).append("\n");
        sb.append("extendRepoId:").append(extendRepoId).append("\n");
        sb.append("extendCommitId:").append(extendCommitId).append("\n");
        sb.append("message:").append(message);
        return sb.toString();
    }

    /**
     * 解析git提交信息的字符串表示形式，生成一个GitCommit对象
     *
     * @param str 字符串形式的git提交信息
     * @return 返回解析后的GitCommit对象
     */
    public static GitCommit parse(String str) {
        // 将输入字符串按行分割，便于逐行解析
        String[] arr = str.split("\n");
        // 创建一个新的GitCommit对象，用于存储解析后的提交信息
        GitCommit commit = new GitCommit();
        // 遍历分割后的每一行，解析其中的提交信息
        for (String s : arr) {
            // 判断当前行的信息类型，并提取相应的内容，存储到commit对象中
            if (s.startsWith("author:")) {
                commit.setAuthor(s.substring(7));
            }
            else if (s.startsWith("time:")) {
                commit.setTime(s.substring(5));
            }
            else if (s.startsWith("tag:")) {
                commit.setTag(s.substring(4));
            }
            else if (s.startsWith("extendRepoId:")) {
                commit.setExtendRepoId(s.substring(13));
            }
            else if (s.startsWith("extendCommitId:")) {
                commit.setExtendCommitId(s.substring(15));
            } else if (s.startsWith("message:")) {
                commit.setMessage(s.substring(8));
            }
        }
        // 返回填充了提交信息的commit对象
        return commit;
    }
}

