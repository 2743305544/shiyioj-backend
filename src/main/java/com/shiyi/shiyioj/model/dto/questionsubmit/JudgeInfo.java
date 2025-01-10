package com.shiyi.shiyioj.model.dto.questionsubmit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 题目判断信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeInfo {
    /**
     * 程序执行信息，单位为毫秒
     */
    private String message;
    /**
     * 消耗的内存，单位为KB
     */
    private Long memoryLimit;
    /**
     * 消耗时间，单位为KB
     */
    private Long time;
}
