package com.kingsware.kdev.core.bean;

import lombok.Data;

/**
 * @author chenp
 * @date 2024/5/8
 */
@Data
public class SsrRes {
    /** md5 **/
    private String md5;
    /** 内容 **/
    private String content;
    /** key **/
    private String key;

    public String toScript() {
        String script = String.format("" +
                "{\n" +
                "    const key = '%s';\n" +
                "    const md5 =  '%s';\n" +
                "    const content = \"222\";\n" +
                "    const md5Key = key + '_md5';\n" +
                "    const lsMd5 = localStorage.get(md5Key) \n" +
                "    if (lsMd5 == undefined && lsMd5 !== md5) {\n" +
                "        const newContent = decodeURIComponent(content)\n" +
                "        localStorage.setItem(key, newContent)\n" +
                "        localStorage.setItem(md5Key, md5)\n" +
                "    }\n" +
                "}\n");
        return script;
    }
}
