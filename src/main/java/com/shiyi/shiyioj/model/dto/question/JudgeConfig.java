package com.shiyi.shiyioj.model.dto.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeConfig {
    /**
     * 时间限制，单位为毫秒
     */
    private Long timeLimit;
    /**
     * 内存限制，单位为KB
     */
    private Long memoryLimit;
    /**
     * 栈大小限制，单位为KB
     */
    private Long stackLimit;
}
