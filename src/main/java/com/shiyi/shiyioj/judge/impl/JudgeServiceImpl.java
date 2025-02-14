package com.shiyi.shiyioj.judge.impl;

import cn.hutool.json.JSONUtil;
import com.shiyi.shiyioj.common.ErrorCode;
import com.shiyi.shiyioj.exception.BusinessException;
import com.shiyi.shiyioj.judge.JudgeManager;
import com.shiyi.shiyioj.judge.JudgeService;
import com.shiyi.shiyioj.judge.codesandbox.CodeSandbox;
import com.shiyi.shiyioj.judge.codesandbox.CodeSandboxFactory;
import com.shiyi.shiyioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.shiyi.shiyioj.judge.codesandbox.model.ExecuteCodeResponse;
import com.shiyi.shiyioj.judge.strategy.JudgeContext;
import com.shiyi.shiyioj.judge.strategy.JudgeStrategy;
import com.shiyi.shiyioj.judge.strategy.ipml.DefaultJudgeStrategy;
import com.shiyi.shiyioj.judge.strategy.ipml.JavaJudgeStrategy;
import com.shiyi.shiyioj.model.dto.question.JudgeCase;
import com.shiyi.shiyioj.model.dto.question.JudgeConfig;
import com.shiyi.shiyioj.model.dto.questionsubmit.JudgeInfo;
import com.shiyi.shiyioj.model.entity.Question;
import com.shiyi.shiyioj.model.entity.QuestionSubmit;
import com.shiyi.shiyioj.model.enums.QuestionSubmitEnum;
import com.shiyi.shiyioj.service.QuestionService;
import com.shiyi.shiyioj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Value("${codesandbox.type}")
    private String codeSandboxType;

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManager judgeManager;

    @Resource
    private CodeSandboxFactory codeSandboxFactory;

    @Override
    public QuestionSubmit doJudge(long submitId) {
        QuestionSubmit questionSubmit = questionSubmitService.getById(submitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if(question == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        if(questionSubmit.getStatus().equals(QuestionSubmitEnum.RUNNING.getValue())){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"该提交正在等待评测中");
        }
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(submitId);
        questionSubmitUpdate.setStatus(QuestionSubmitEnum.WAITING.getValue());

        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if(!update){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"更新失败");
        }
        CodeSandbox codeSandbox = codeSandboxFactory.createCodeSandbox(codeSandboxType);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        String judgeCase = question.getJudgeCase();
        Integer timeLimit = JSONUtil.toBean(question.getJudgeConfig(), JudgeConfig.class).getTimeLimit();
        List<JudgeCase> list = JSONUtil.toList(judgeCase, JudgeCase.class);
        List<String> inputs = list.stream().map(JudgeCase::getInput).toList();
        ExecuteCodeRequest build = ExecuteCodeRequest.builder()
                .time(timeLimit)
                .code(code)
                .language(language)
                .inputList(inputs)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(build);
        List<String> outputList = executeCodeResponse.getOutputList();
        JudgeContext judgeContext = JudgeContext.builder()
                .question(question)
                .judgeCase(list)
                .outputs(outputList)
                .inputs(inputs)
                .judgeInfo(executeCodeResponse.getJudgeInfo())
                .questionSubmit(questionSubmit)
                .build();

        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(submitId);
        questionSubmitUpdate.setStatus(QuestionSubmitEnum.SUCCESS.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if(!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"系统判题出现问题");
        }
        return questionSubmitService.getById(submitId);

    }
}
