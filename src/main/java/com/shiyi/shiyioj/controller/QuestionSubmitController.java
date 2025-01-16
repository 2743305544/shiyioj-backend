package com.shiyi.shiyioj.controller;

import co.elastic.clients.elasticsearch.sql.QueryRequest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shiyi.shiyioj.annotation.AuthCheck;
import com.shiyi.shiyioj.common.BaseResponse;
import com.shiyi.shiyioj.common.ErrorCode;
import com.shiyi.shiyioj.common.ResultUtils;
import com.shiyi.shiyioj.constant.UserConstant;
import com.shiyi.shiyioj.exception.BusinessException;

import com.shiyi.shiyioj.model.dto.question.QuestionQueryRequest;
import com.shiyi.shiyioj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.shiyi.shiyioj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.shiyi.shiyioj.model.entity.Question;
import com.shiyi.shiyioj.model.entity.QuestionSubmit;
import com.shiyi.shiyioj.model.entity.User;
import com.shiyi.shiyioj.model.vo.QuestionAllVo;
import com.shiyi.shiyioj.model.vo.QuestionSubmitVo;
import com.shiyi.shiyioj.service.QuestionSubmitService;
import com.shiyi.shiyioj.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 *
 * @author <a href="https://github.com/lishiyi">程序员鱼皮</a>
 * @from <a href="https://shiyi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return resultNum 本次点赞变化数
     */
    @PostMapping("/")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
            HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        final User loginUser = userService.getLoginUser(request);
        long result = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(result);
    }

    /**
     * 分页获取列表（仅管理员）
     *
     * @param questionSubmitQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionSubmitVo>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest, HttpServletRequest request) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        Page<QuestionSubmit> questionPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        final User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(questionSubmitService.getQuestionSubmitVoPage(questionPage, loginUser));
    }

}
