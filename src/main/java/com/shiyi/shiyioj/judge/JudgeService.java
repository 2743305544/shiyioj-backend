package com.shiyi.shiyioj.judge;

import com.shiyi.shiyioj.model.entity.QuestionSubmit;
import com.shiyi.shiyioj.model.vo.QuestionSubmitVo;

public interface JudgeService {
    QuestionSubmit doJudge(long submitId);
}
