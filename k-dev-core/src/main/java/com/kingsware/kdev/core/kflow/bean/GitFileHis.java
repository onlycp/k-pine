package com.kingsware.kdev.core.kflow.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The GitHistory class represents the history information of a Git commit.
 * It includes the content of the commit and the commit message.
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2024/9/2 17:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GitFileHis {

    // The content of the commit
    private String content;

    // The message of the commit
    private String commit;
}
