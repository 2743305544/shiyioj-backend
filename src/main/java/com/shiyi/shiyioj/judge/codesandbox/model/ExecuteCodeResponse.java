package com.shiyi.shiyioj.judge.codesandbox.model;

import com.shiyi.shiyioj.model.dto.questionsubmit.JudgeInfo;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class ExecuteCodeResponse {
    private List<String> outputList;
    private String message;
    private Integer status;
    private JudgeInfo judgeInfo;
}
