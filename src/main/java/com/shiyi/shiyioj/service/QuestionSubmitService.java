package com.shiyi.shiyioj.service;
import co.elastic.clients.elasticsearch.sql.QueryRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shiyi.shiyioj.model.dto.question.QuestionQueryRequest;
import com.shiyi.shiyioj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.shiyi.shiyioj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.shiyi.shiyioj.model.entity.Question;
import com.shiyi.shiyioj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shiyi.shiyioj.model.entity.User;
import com.shiyi.shiyioj.model.vo.QuestionSubmitVo;
import com.shiyi.shiyioj.model.vo.QuestionVo;

import javax.servlet.http.HttpServletRequest;

/**
* @author 34011
* @description 针对表【question_submit(帖子题目提交)】的数据库操作Service
* @createDate 2024-12-28 21:09:20
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 帖子题目提交（内部服务）
     *
     * @param userId
     * @param questionId
     * @return
     */
    int doQuestionSubmitInner(long userId, long questionId);


    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param loginUser
     * @param questionSubmit
     * @return
     */
    QuestionSubmitVo getQuestionSubmitVo(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVo> getQuestionSubmitVoPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
