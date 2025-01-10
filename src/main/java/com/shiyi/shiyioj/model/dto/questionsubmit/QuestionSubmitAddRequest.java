package com.shiyi.shiyioj.model.dto.questionsubmit;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 创建请求
 *
 * @author <a href="https://github.com/lishiyi">程序员鱼皮</a>
 * @from <a href="https://shiyi.icu">编程导航知识星球</a>
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
    private Integer questionId;

    /**
     * 代码
     */
    private String code;
    /**
     * 是否删除
     */
    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}
