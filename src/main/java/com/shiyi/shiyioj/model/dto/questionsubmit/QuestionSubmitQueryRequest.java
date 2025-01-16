package com.shiyi.shiyioj.model.dto.questionsubmit;

import com.shiyi.shiyioj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @author <a href="https://github.com/lishiyi">程序员鱼皮</a>
 * @from <a href="https://shiyi.icu">编程导航知识星球</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {

    /**
     * 提交语言
     */
    private String language;

    /**
     * 题目 id
     */
    private Integer questionId;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 代码
     */
    private String code;


    /**
     * 状态
     */
    private Integer status;


    private static final long serialVersionUID = 1L;
}
