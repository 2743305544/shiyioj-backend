package com.shiyi.shiyioj.judge;

import com.shiyi.shiyioj.judge.strategy.JudgeContext;
import com.shiyi.shiyioj.judge.strategy.JudgeStrategy;
import com.shiyi.shiyioj.judge.strategy.ipml.DefaultJudgeStrategy;
import com.shiyi.shiyioj.judge.strategy.ipml.JavaJudgeStrategy;
import com.shiyi.shiyioj.model.dto.questionsubmit.JudgeInfo;
import org.springframework.stereotype.Component;

@Component
public class JudgeManager {
    public JudgeInfo doJudge(JudgeContext judgeContext){
        String language = judgeContext.getQuestionSubmit().getLanguage();
        JudgeStrategy judgeStrategy = null;
        if("java".equals(language)){
            judgeStrategy = new JavaJudgeStrategy();
        }else {
            judgeStrategy = new DefaultJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
