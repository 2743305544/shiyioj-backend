package com.shiyi.shiyioj.judge.codesandbox;

import com.shiyi.shiyioj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.shiyi.shiyioj.judge.codesandbox.impl.OtherCodeSandbox;
import com.shiyi.shiyioj.judge.codesandbox.impl.RemoteCodeSandbox;

/**
 * 代码沙箱工厂
 */
public class CodeSandboxFactory {
    public static CodeSandbox createCodeSandbox(String type) {
        return switch (type) {
            case "example" -> new ExampleCodeSandbox();
            case "other" -> new OtherCodeSandbox();
            case "remote" -> new RemoteCodeSandbox();
            default -> throw new RuntimeException("不支持的代码沙箱类型");
        };
    }
}
