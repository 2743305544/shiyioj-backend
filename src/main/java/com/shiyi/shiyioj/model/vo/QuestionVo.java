package com.shiyi.shiyioj.model.vo;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.shiyi.shiyioj.model.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionVo {
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
     * 题目提交数
     */
    private Integer submitNum;

    /**
     * 题目通过数
     */
    private Integer acceptNum;

    /**
     * 评测配置 (json 对象)
     */
    private JSONConfig judgeConfig;

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
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private UserVO userVO;

    public static QuestionVo objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionVo questionVo = new QuestionVo();
        BeanUtils.copyProperties(question, questionVo);
        questionVo.setTags(JSONUtil.toList(question.getTags(), String.class));
        questionVo.setJudgeConfig(JSONUtil.toBean(question.getJudgeConfig(), JSONConfig.class));
        return questionVo;
    }
    public static Question voToObj(QuestionVo questionVo) {
        if (questionVo == null) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionVo, question);
        List<String> tags1 = questionVo.getTags();
        if(tags1 != null) {
            question.setTags(JSONUtil.toJsonStr(tags1));
        }
        JSONConfig judgeConfig1 = questionVo.getJudgeConfig();
        if(judgeConfig1 != null) {
            question.setJudgeConfig(JSONUtil.toJsonStr(judgeConfig1));
        }
        return question;
    }
}
