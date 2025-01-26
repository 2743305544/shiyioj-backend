package com.shiyi.shiyioj.judge.codesandbox;

import com.shiyi.shiyioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.shiyi.shiyioj.judge.codesandbox.model.ExecuteCodeResponse;

public interface CodeSandbox {
    /**
     * 执行代码
     * @param request
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest request);
}
