package com.shiyi.shiyioj.service;
import com.shiyi.shiyioj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.shiyi.shiyioj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shiyi.shiyioj.model.entity.User;

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
    int doQuestionSubmitInner(long userId, int questionId);
}
