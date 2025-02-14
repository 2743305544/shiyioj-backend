package com.shiyi.shiyioj.judge.codesandbox;

import com.shiyi.shiyioj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.shiyi.shiyioj.judge.codesandbox.impl.OtherCodeSandbox;
import com.shiyi.shiyioj.judge.codesandbox.impl.RemoteCodeSandbox;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 代码沙箱工厂
 */
@Component
public class CodeSandboxFactory {

    @Resource
    private  ExampleCodeSandbox exampleCodeSandbox;

    public CodeSandbox createCodeSandbox(String type) {
        return switch (type) {
            case "example" -> exampleCodeSandbox;
            case "other" -> new OtherCodeSandbox();
            case "remote" -> new RemoteCodeSandbox();
            default -> throw new RuntimeException("不支持的代码沙箱类型");
        };
    }
}
