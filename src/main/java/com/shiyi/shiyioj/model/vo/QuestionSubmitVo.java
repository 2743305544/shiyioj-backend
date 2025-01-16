package com.shiyi.shiyioj.model.vo;


import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.shiyi.shiyioj.model.dto.question.JudgeConfig;
import com.shiyi.shiyioj.model.dto.questionsubmit.JudgeInfo;
import com.shiyi.shiyioj.model.entity.Question;
import com.shiyi.shiyioj.model.entity.QuestionSubmit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionSubmitVo {
    /**
     * id
     */
    private Long id;

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
     * 判题信息(json 数组)
     */
    private JudgeInfo judgeInfo;

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
     * 提交者信息
     */
    private UserVO user;

    public static QuestionSubmitVo objToVo(QuestionSubmit question) {
        if (question == null) {
            return null;
        }
        QuestionSubmitVo questionVo = new QuestionSubmitVo();
        BeanUtils.copyProperties(question, questionVo);
        questionVo.setJudgeInfo(JSONUtil.toBean(question.getJudgeInfo(), JudgeInfo.class));
        return questionVo;
    }
    public static QuestionSubmit voToObj(QuestionSubmitVo questionVo) {
        if (questionVo == null) {
            return null;
        }
        QuestionSubmit question = new QuestionSubmit();
        BeanUtils.copyProperties(questionVo, question);
        JudgeInfo judgeInfo1 = questionVo.getJudgeInfo();
        if(judgeInfo1 != null) {
            question.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo1));
        }
        return question;
    }
}
