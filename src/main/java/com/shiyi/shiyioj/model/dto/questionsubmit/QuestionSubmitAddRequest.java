package com.shiyi.shiyioj.model.dto.questionsubmit;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 创建请求
 *
 *  
 */
@Data
public class QuestionSubmitAddRequest implements Serializable {

    /**
     * 提交语言
     */
    private String language;

    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 代码
     */
    private String code;

    private static final long serialVersionUID = 1L;
}
