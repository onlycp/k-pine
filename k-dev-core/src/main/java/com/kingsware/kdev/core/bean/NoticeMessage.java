package com.kingsware.kdev.core.bean;

import lombok.Data;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/7/11 09:21
 */
@Data
public class NoticeMessage {

    private String id;
    private String title;
    private String content;
    private String toWhos;
    private String fromWho;
}
