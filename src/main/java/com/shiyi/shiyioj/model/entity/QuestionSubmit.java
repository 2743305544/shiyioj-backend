package com.shiyi.shiyioj.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import lombok.Data;

/**
 * 帖子点赞
 * @TableName question_submit
 */
@TableName(value ="question_submit")
@Data
public class QuestionSubmit {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

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

    /**
     * 判题信息(json 数组)
     */
    private String judgeInfo;

    /**
     * 状态：0-未判题，1-判题中 2-通过，3-未通过
     */
    private Integer status;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;
}
