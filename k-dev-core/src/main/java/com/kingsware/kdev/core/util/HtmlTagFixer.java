package com.kingsware.kdev.core.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.*;

public class HtmlTagFixer {
    // 需要自闭合的标准标签列表（根据HTML5规范）
    private static final Set<String> VOID_TAGS;

    static {
        VOID_TAGS = new HashSet<>();
        VOID_TAGS.add("area");
        VOID_TAGS.add("base");
        VOID_TAGS.add("br");
        VOID_TAGS.add("col");
        VOID_TAGS.add("embed");
        VOID_TAGS.add("hr");
        VOID_TAGS.add("img");
        VOID_TAGS.add("input");
        VOID_TAGS.add("link");
        VOID_TAGS.add("meta");
        VOID_TAGS.add("param");
        VOID_TAGS.add("source");
        VOID_TAGS.add("track");
        VOID_TAGS.add("wbr");
    }

    /**
     * 智能修复未闭合标签（改进版）
     * @param html 输入的 HTML 字符串
     * @return 修复后的 HTML 字符串
     */
    public static String fixUnclosedTags(String html) {
        if (html == null || html.isEmpty()) return html;

        // 优化后的正则表达式（不区分大小写，排除注释/CDATA）
        Pattern pattern = Pattern.compile(
                "(?i)" +                          // 不区分大小写
                        "(<!\\[CDATA\\[.*?]]>)" +     // 排除CDATA块
                        "|(<!--.*?-->)" +                 // 排除注释
                        "|<(/?)([A-Za-z]+)([^>]*?)(?<!/)(>)" // 捕获标签
        );

        StringBuffer sb = new StringBuffer();
        Matcher matcher = pattern.matcher(html);

        while (matcher.find()) {
            // 处理CDATA和注释
            if (matcher.group(1) != null || matcher.group(2) != null) {
                matcher.appendReplacement(sb, matcher.group());
                continue;
            }

            String closeSlash = matcher.group(2); // 闭合标签的斜杠
            String tagName = matcher.group(3).toLowerCase();
            String attributes = matcher.group(4);
            String endBracket = matcher.group(5);

            // 跳过闭合标签和需要保留的非自闭合标签
            if (!closeSlash.isEmpty() || !VOID_TAGS.contains(tagName)) {
                matcher.appendReplacement(sb, matcher.group());
                continue;
            }

            // 构建修复后的标签
            String replacement = String.format("<%s%s%s/>",
                    closeSlash,
                    tagName,
                    attributes.replaceAll("\\s+$", "")
            );
            matcher.appendReplacement(sb, replacement);
        }
        matcher.appendTail(sb);

        return sb.toString();
    }
}
