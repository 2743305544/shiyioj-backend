package com.shiyi.shiyioj.judge.strategy.ipml;

import cn.hutool.json.JSONUtil;
import com.shiyi.shiyioj.judge.strategy.JudgeContext;
import com.shiyi.shiyioj.judge.strategy.JudgeStrategy;
import com.shiyi.shiyioj.model.dto.question.JudgeCase;
import com.shiyi.shiyioj.model.dto.question.JudgeConfig;
import com.shiyi.shiyioj.model.dto.questionsubmit.JudgeInfo;
import com.shiyi.shiyioj.model.entity.Question;
import com.shiyi.shiyioj.model.enums.JudgeInfoEnum;

import java.util.List;

/**
 * 对于java语言的提交特判
 */
public class JavaJudgeStrategy implements JudgeStrategy {
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfo1 = judgeContext.getJudgeInfo();

        int memory = judgeInfo1.getMemory().intValue();
        int time = judgeInfo1.getTime().intValue();

        List<String> inputs = judgeContext.getInputs();
        List<String> outputList = judgeContext.getOutputs();
        Question question = judgeContext.getQuestion();
        List<JudgeCase> list = judgeContext.getJudgeCase();
        JudgeInfoEnum judgeInfoEnum = JudgeInfoEnum.ACCEPTED;

        JudgeInfo judgeInfoRes = JudgeInfo.builder()
                .message(judgeInfoEnum.getValue())
                .memory((long) memory)
                .time((long) time)
                .build();
        if (judgeContext.getStatus() == -1){
            judgeInfoEnum = JudgeInfoEnum.DANGEROUS_OPERATION;
            judgeInfoRes.setMessage(judgeInfoEnum.getValue());
            return judgeInfoRes;
        }
        if (judgeContext.getStatus() == 2){
            judgeInfoEnum = JudgeInfoEnum.COMPILE_ERROR;
            judgeInfoRes.setMessage(judgeInfoEnum.getValue());
            return judgeInfoRes;
        }
        if(outputList.size() != inputs.size()){
            if(outputList.size() >= inputs.size()){
                judgeInfoEnum = JudgeInfoEnum.OUTPUT_LIMIT_EXCEEDED;
                judgeInfoRes.setMessage(judgeInfoEnum.getValue());
                return judgeInfoRes;
            }
            judgeInfoEnum = JudgeInfoEnum.WRONG_ANSWER;
            judgeInfoRes.setMessage(judgeInfoEnum.getValue());
            return judgeInfoRes;
        }
        for (int i = 0; i < list.size() ; i++) {
            JudgeCase judgeCase1 = list.get(i);
            if(!judgeCase1.getOutput().equals(outputList.get(i))){
                judgeInfoEnum = JudgeInfoEnum.WRONG_ANSWER;
                judgeInfoRes.setMessage(judgeInfoEnum.getValue());
                return judgeInfoRes;
            }
        }

        String judgeConfig = question.getJudgeConfig();
        JudgeConfig judgeConfig1 = JSONUtil.toBean(judgeConfig, JudgeConfig.class);
        Integer needMemoryLimit1 = judgeConfig1.getMemoryLimit();
        Integer needTimeLimit = judgeConfig1.getTimeLimit();
        if(memory/3 > needMemoryLimit1){
            judgeInfoEnum = JudgeInfoEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoRes.setMessage(judgeInfoEnum.getValue());
            return judgeInfoRes;
        }
        if(time/2 > needTimeLimit){
            judgeInfoEnum = JudgeInfoEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoRes.setMessage(judgeInfoEnum.getValue());
            return judgeInfoRes;
        }
        return judgeInfoRes;
    }
}
