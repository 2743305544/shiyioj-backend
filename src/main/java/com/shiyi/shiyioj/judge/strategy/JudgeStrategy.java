package com.shiyi.shiyioj.judge.strategy;

import com.shiyi.shiyioj.model.dto.questionsubmit.JudgeInfo;

public interface JudgeStrategy {
    JudgeInfo doJudge(JudgeContext judgeContext);
}
