package com.shiyi.shiyioj.model.vo;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shiyi.shiyioj.config.JsonSerializeConfig;
import com.shiyi.shiyioj.model.dto.question.JudgeCase;
import com.shiyi.shiyioj.model.dto.question.JudgeConfig;
import com.shiyi.shiyioj.model.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAllVo implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;

    /**
     * 答案
     */
    private String answer;

    /**
     * 题目提交数
     */
    private Integer submitNum;

    /**
     * 题目通过数
     */
    private Integer acceptNum;

    /**
     * 测试用例(json 数组)
     */
    private List<JudgeCase> judgeCase;

    /**
     * 评测配置 (json 对象)
     */
    private JudgeConfig judgeConfig;

    /**
     * 点赞数
     */
    private Integer thumbNum;

    /**
     * 收藏数
     */
    private Integer favourNum;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    @JsonSerialize(using = JsonSerializeConfig.CustomDateSerializer.class)
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonSerialize(using = JsonSerializeConfig.CustomDateSerializer.class)
    private Date updateTime;

    public static QuestionAllVo objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionAllVo questionVo = new QuestionAllVo();
        BeanUtils.copyProperties(question, questionVo);
        questionVo.setTags(JSONUtil.toList(question.getTags(), String.class));
        questionVo.setJudgeConfig(JSONUtil.toBean(question.getJudgeConfig(), JudgeConfig.class));
        questionVo.setJudgeCase(JSONUtil.toList(question.getJudgeCase(), JudgeCase.class));
        return questionVo;
    }
    public static Question voToObj(QuestionAllVo questionAllVo) {
        if (questionAllVo == null) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionAllVo, question);
        List<String> tags1 = questionAllVo.getTags();
        if(tags1 != null) {
            question.setTags(JSONUtil.toJsonStr(tags1));
        }
        JudgeConfig judgeConfig1 = questionAllVo.getJudgeConfig();
        if(judgeConfig1 != null) {
            question.setJudgeConfig(JSONUtil.toJsonStr(judgeConfig1));
        }
        List<JudgeCase> judgeCase1 = questionAllVo.getJudgeCase();
        if(judgeCase1 != null) {
            question.setJudgeCase(JSONUtil.toJsonStr(judgeCase1));
        }
        return question;
    }
}
