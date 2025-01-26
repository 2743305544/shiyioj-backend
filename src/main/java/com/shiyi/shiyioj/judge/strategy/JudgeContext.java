package com.shiyi.shiyioj.judge.strategy;

import com.shiyi.shiyioj.model.dto.question.JudgeCase;
import com.shiyi.shiyioj.model.dto.questionsubmit.JudgeInfo;
import com.shiyi.shiyioj.model.entity.Question;
import com.shiyi.shiyioj.model.entity.QuestionSubmit;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class JudgeContext {
    private JudgeInfo judgeInfo;
    private List<String> inputs;
    private List<String> outputs;
    private Question question;
    private List<JudgeCase> judgeCase;
    private QuestionSubmit questionSubmit;
}
