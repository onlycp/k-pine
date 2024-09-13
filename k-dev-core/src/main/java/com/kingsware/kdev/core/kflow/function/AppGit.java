package com.kingsware.kdev.core.kflow.function;

import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.kflow.bean.GitCommit;
import com.kingsware.kdev.core.kflow.bean.GitFile;
import com.kingsware.kdev.core.kflow.bean.GitFileHis;
import com.kingsware.kdev.core.kflow.bean.KOperationRet;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.KdbRet;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.FaasInvoke;
import com.kingsware.kdev.core.util.JsonUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * git操作类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2024/8/29 09:25
 */
@SuppressWarnings("all")
public class AppGit {

    private final String repoId;

    public AppGit(String repoId) {
        this.repoId = repoId;
    }

    /**
     * 获取Git仓库的基础路径
     * 该方法用于确定Git仓库存储的基础目录，考虑到可能根据不同环境或配置有不同的路径设置
     * @return 返回配置的Git仓库基础路径，默认为"AppGit"
     */
    private String getBasePath() {
        return SpringContext.getProperties("git.base-path", "AppGit");
    }

    /**
     * 执行Kdb接口的脚本
     * @param script
     * @param variables
     */
    private String execute(String script, Map<String, Object> variables) {

        // 调用数据库API执行脚本，实现仓库初始化
        KdbRet<String> ret = DB.kdbApi().executeScript(script, variables);
        // 如果执行失败，抛出业务异常
        if (ret.getErrorCode() != 0) {
            throw BusinessException.serviceThrow(ret.getMessage());
        }
        String responseBody = ret.getResponseBody();
        KOperationRet oper = JsonUtil.toBean(responseBody, KOperationRet.class);
        if (oper.getStatus() != 0) {
            throw BusinessException.serviceThrow(ret.getMessage());
        }
        return oper.getData();

    }

    /**
     * 创建一个新的Git提交对象
     * 此方法用于初始化一个GitCommit对象，设置提交的基本信息
     *
     * @param message 提交信息，记录本次提交的描述和原因
     * @return 返回初始化完毕的GitCommit对象
     */
    public GitCommit getCommit(String message) {
        // 创建GitCommit实例
        GitCommit commit = new GitCommit();
        // 设置提交者为当前上下文的用户名
        commit.setAuthor(KClientContext.getContext() == null ? "admin": KClientContext.getContext().getUsername());
        // 设置扩展提交ID为仓库ID，用于跟踪仓库
        commit.setExtendCommitId(this.repoId);
        // 设置提交时间为当前时间
        commit.setTime(DateUtils.getNow());
        // 初始化标签为空字符串，留待后续可能的设置
        commit.setTag("");
        // 设置提交信息为参数提供的信息
        commit.setMessage(message);
        // 返回构建完成的提交对象
        return commit;
    }

    /**
     * 创建一个新的Git提交对象
     * 此方法用于初始化一个GitCommit对象，设置提交的基本信息
     *
     * @param message 提交信息，记录本次提交的描述和原因
     * @return 返回初始化完毕的GitCommit对象
     */
    public GitCommit getCommit(String author, String time, String message) {
        // 创建GitCommit实例
        GitCommit commit = new GitCommit();
        // 设置提交者为当前上下文的用户名
        commit.setAuthor(author);
        // 设置扩展提交ID为仓库ID，用于跟踪仓库
        commit.setExtendRepoId(this.repoId);
        // 设置提交时间为当前时间
        commit.setTime(time);
        // 初始化标签为空字符串，留待后续可能的设置
        commit.setTag("");
        // 设置提交信息为参数提供的信息
        commit.setMessage(message);
        // 返回构建完成的提交对象
        return commit;
    }



    /**
     * 初始化Git仓库
     * 根据给定的仓库标识符，准备和初始化对应的Git仓库主要用于在系统中创建或重置一个Git仓库的场景
     */
    public void initRepo() {
        // 初始化变量Map，用于存放脚本执行时所需的变量值
        Map<String, Object> variables = new HashMap<>(1);
        // 构造仓库路径，结合基础路径和仓库ID
        variables.put("repoPath", getBasePath() + "/" + repoId);
        // 定义q语言脚本，调用git.init函数来初始化仓库
        String script = "git.init(getResult('repoPath'));";
        // 执行脚本
        execute(script, variables);
    }


    /**
     * 检查指定的仓库ID对应的仓库是否存在
     *
     * @return Boolean值，存在返回true，否则返回false
     */
    public Boolean hasRepo() {
        // 初始化变量Map，用于存放脚本执行时所需的变量值
        Map<String, Object> variables = new HashMap<>(1);
        // 构造仓库路径，结合基础路径和仓库ID
        variables.put("repoPath", getBasePath() + "/" + repoId);
        // 定义查询仓库是否存在的脚本，使用getResult方法获取变量值
        String result = execute("git.existRepo(getResult('repoPath'));", variables);
        Map<String, Object> resultMap = JsonUtil.toBean(result, Map.class);
        // 返回查询结果
        return (Boolean) resultMap.get("value");
    }


    /**
     * 批量向指定仓库添加文件
     *
     * @param gitFiles 待添加的文件列表，包含每个文件的路径信息
     * @throws BusinessException 如果执行脚本出错，则抛出业务异常
     *
     * 该方法首先遍历待添加的文件列表，对每个文件的路径进行处理，加上基础路径和仓库ID，
     * 以形成完整的文件系统路径。然后，将处理后的文件列表转换为JSON格式的字符串，
     * 与其他必要的变量一起，作为脚本执行时的输入参数。接下来，调用Kdb接口执行
     * 一个添加文件的脚本，该脚本使用传入的仓库路径和文件列表进行操作。如果脚本执行
     * 出错，即返回的错误码不为0，则抛出业务异常。
     */
    public void addFiles(List<GitFile> gitFiles) {
        // 处理gitFile, 加上basePath
//        for (GitFile gitFile : gitFiles) {
//            gitFile.setPath(getBasePath() + "/" + repoId + "/" + gitFile.getPath());
//        }
        // 初始化变量Map，用于存放脚本执行时所需的变量值
        Map<String, Object> variables = new HashMap<>(2);
        variables.put("gitFiles", JsonUtil.toJson(gitFiles));
        variables.put("repoPath", getBasePath() + "/" + repoId);
        // 定义添加文件的脚本，使用getResult方法获取变量值
        String script = "git.addMultipleFileByContent(getResult('repoPath'), context.get('gitFiles'));";
        // 执行脚本
        execute(script, variables);
    }

    public void batchAddCommit(String filePath, List<GitFileHis> gitFileHis)  {
        // 处理gitFile, 加上basePath
//        for (GitFile gitFile : gitFiles) {
//            gitFile.setPath(getBasePath() + "/" + repoId + "/" + gitFile.getPath());
//        }
        // 初始化变量Map，用于存放脚本执行时所需的变量值
        Map<String, Object> variables = new HashMap<>(2);
        variables.put("filePath", filePath);
        variables.put("gitFileHis", JsonUtil.toJson(gitFileHis));
        variables.put("repoPath", getBasePath() + "/" + repoId);
        // 定义添加文件的脚本，使用getResult方法获取变量值
        String script = "git.batchAddCommit(getResult('repoPath'), context.get('filePath'), context.get('gitFileHis'));";
        // 执行脚本
        execute(script, variables);
    }


    /**
     * 批量向指定仓库添加文件
     *
     * @param gitFiles 待添加的文件列表，包含每个文件的路径信息
     * @throws BusinessException 如果执行脚本出错，则抛出业务异常
     *
     * 该方法首先遍历待添加的文件列表，对每个文件的路径进行处理，加上基础路径和仓库ID，
     * 以形成完整的文件系统路径。然后，将处理后的文件列表转换为JSON格式的字符串，
     * 与其他必要的变量一起，作为脚本执行时的输入参数。接下来，调用Kdb接口执行
     * 一个添加文件的脚本，该脚本使用传入的仓库路径和文件列表进行操作。如果脚本执行
     * 出错，即返回的错误码不为0，则抛出业务异常。
     */
    public void addCommitFile(GitFile gitFile, GitCommit gitCommit) {
        // 处理gitFile, 加上basePath
//        for (GitFile gitFile : gitFiles) {
//            gitFile.setPath(getBasePath() + "/" + repoId + "/" + gitFile.getPath());
//        }
        // 初始化变量Map，用于存放脚本执行时所需的变量值
        Map<String, Object> variables = new HashMap<>(4);
        variables.put("repoPath", getBasePath() + "/" + repoId);
        variables.put("filePath", gitFile.getPath());
        variables.put("content", gitFile.getContent());
        variables.put("commitMessage", gitCommit.toString());
        // 定义添加文件的脚本，使用getResult方法获取变量值
        String script = "git.addSingleFileByContentWithCommit(getResult('repoPath'), context.get('filePath'), context.get('content'), context.get('commitMessage'));";
        // 执行脚本
        execute(script, variables);
    }



    /**
     * 提交更改到Git仓库
     *
     * @param gitCommit 包含提交信息的对象，用于构造提交消息
     * 该方法通过构造提交脚本并执行，实现对指定Git仓库的更改提交
     */
    public void commit(GitCommit gitCommit) {
        // 初始化变量Map，用于存放脚本执行时所需的变量值
        Map<String, Object> variables = new HashMap<>(2);
        // 组合仓库基础路径和仓库ID，得到完整的仓库路径
        variables.put("repoPath", getBasePath() + "/" + repoId);
        // 将Git提交对象转换为字符串，作为提交信息
        variables.put("commit", gitCommit.toString());
        // 定义执行脚本，用于执行Git提交操作
        String script = "git.commit(getResult('repoPath'), context.get('commit'));";
        // 执行脚本，完成Git提交操作
        execute(script, variables);
    }

    /**
     * 添加标签到Git仓库
     * @param tagName 标签名，用于创建新的Git标签
     * @param message 标签信息，描述该标签的用途或它所标记的版本
     */
    public void addTag(String tagName, String message) {
        // 初始化变量Map，用于存储脚本执行所需的各种变量
        Map<String, Object> variables = new HashMap<>(2);
        // 组合仓库基础路径和仓库ID，得到完整的仓库路径
        variables.put("repoPath", getBasePath() + "/" + repoId);
        // 存储标签名，作为待创建的标签名称
        variables.put("tagName", tagName);
        // 存储标签信息，作为待创建的标签的描述
        variables.put("message", message);
        // 定义执行脚本，用于执行Git添加标签操作
        String script = "git.addTag(getResult('repoPath'), context.get('tagName'), context.get('message'));";
        // 执行脚本，完成Git添加标签操作
        execute(script, variables);
    }



    /**
     * 删除指定仓库中的文件
     *
     * @param filePath 文件路径，表示在仓库中要删除的文件位置
     */
    public void deleteFile(String filePath) {
        // 初始化变量Map，用于存放脚本执行时所需的变量值
        Map<String, Object> variables = new HashMap<>(2);
        // 组合仓库基础路径和仓库ID，得到完整的仓库路径
        variables.put("repoPath", getBasePath() + "/" + repoId);
        // 将文件路径存入变量，以便在脚本中使用
        variables.put("filePath", filePath);
        // 定义执行脚本，用于执行删除文件操作
        String script = "git.removeSingleFile(getResult('repoPath'), getResult('filePath'));";
        // 执行脚本，完成文件删除操作
        execute(script, variables);
    }

    /**
     * 删除指定仓库中的多个文件并做一次提交
     *
     * @param filePathList 文件路径列表
     * @param gitCommit 提交信息
     */
    public void deleteCommitMultiFile(List<String> filePathList, GitCommit gitCommit) {
        // 初始化变量Map，用于存放脚本执行时所需的变量值
        Map<String, Object> variables = new HashMap<>(2);
        variables.put("repoPath", getBasePath() + "/" + repoId);
        variables.put("filePath", JsonUtil.toJson(filePathList));
        variables.put("commitMessage", gitCommit.toString());
        // 定义执行脚本，用于执行删除文件操作
        String script = "git.removeMultipleFileWithCommit(context.get('repoPath'), context.get('filePath'), context.get('commitMessage'));";
        // 执行脚本，完成文件删除操作
        execute(script, variables);
    }


    /**
     * 删除指定的仓库
     *
     */
    public  void deleteRepo() {
        // 初始化变量Map，用于存放脚本执行时所需的变量值
        Map<String, Object> variables = new HashMap<>(1);
        // 组合仓库基础路径和仓库ID，得到完整的仓库路径
        variables.put("repoPath", getBasePath() + "/" + repoId);
        // 定义执行脚本，用于执行删除文件操作
        String script = "git.deleteRepo(getResult('repoPath'));";
        // 执行脚本，完成文件删除操作
        execute(script, variables);
    }

}
