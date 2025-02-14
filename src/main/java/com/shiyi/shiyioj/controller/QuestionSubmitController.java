package com.shiyi.shiyioj.controller;

import co.elastic.clients.elasticsearch.sql.QueryRequest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shiyi.shiyioj.annotation.AuthCheck;
import com.shiyi.shiyioj.common.BaseResponse;
import com.shiyi.shiyioj.common.ErrorCode;
import com.shiyi.shiyioj.common.ResultUtils;
import com.shiyi.shiyioj.constant.UserConstant;
import com.shiyi.shiyioj.exception.BusinessException;

import com.shiyi.shiyioj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.shiyi.shiyioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.shiyi.shiyioj.judge.codesandbox.model.ExecuteCodeResponse;
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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 题目提交接口
 *
 *  
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

//    @GetMapping("/test")
//    public void test() {
//        String code = """
//                import java.io.PrintStream;
//                import java.nio.charset.StandardCharsets;
//                import java.util.Scanner;
//
//                public class Main
//                {
//                            public static void main(String args[]) throws Exception
//                            {
//                                    Scanner cin=new Scanner(System.in);
//                                    int a=cin.nextInt(),b=cin.nextInt();
//                //                    System.out.println("结果:"+a+b);
//                                System.out.println("中文" + args[0]);
//                            }
//                }
//                """;
//        String language = "java";
//        List<String> inputList = List.of("1 2", "2 3");
//        ExecuteCodeRequest request = new ExecuteCodeRequest(inputList, language, code,1000);
//        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(request);
//        System.out.println(executeCodeResponse);
//    }

}
