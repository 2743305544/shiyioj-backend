package com.shiyi.shiyioj.judge.codesandbox.impl;

import com.shiyi.shiyioj.judge.codesandbox.CodeSandbox;
import com.shiyi.shiyioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.shiyi.shiyioj.judge.codesandbox.model.ExecuteCodeResponse;
import com.shiyi.shiyioj.model.dto.questionsubmit.JudgeInfo;
import com.shiyi.shiyioj.model.enums.QuestionSubmitEnum;

/**
 * 演示 测试
 */
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest request) {
        return ExecuteCodeResponse.builder()
                .message("测试")
                .judgeInfo(JudgeInfo.builder()
                        .memory(1L)
                        .time(1L)
                        .message("OK")
                        .build())
                .status(QuestionSubmitEnum.SUCCESS.getValue())
                .outputList(request.getInputList())
                .build();
    }
}
