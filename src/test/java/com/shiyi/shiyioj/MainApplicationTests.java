package com.shiyi.shiyioj;

import com.shiyi.shiyioj.judge.codesandbox.CodeSandbox;
import com.shiyi.shiyioj.judge.codesandbox.CodeSandboxFactory;
import com.shiyi.shiyioj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.shiyi.shiyioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.shiyi.shiyioj.judge.codesandbox.model.ExecuteCodeResponse;
import com.shiyi.shiyioj.model.enums.QuestionSubmitEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * 主类测试
 *
 *  
 */
@SpringBootTest
class MainApplicationTests {

    @Autowired
    private ExampleCodeSandbox codeSandbox;

    @Test
    void contextLoads() {
//        CodeSandbox codeSandbox = codeSandboxFactory.createCodeSandbox("example");
        String code = """
                import java.io.PrintStream;
                import java.nio.charset.StandardCharsets;
                import java.util.Scanner;

                public class Main
                {
                            public static void main(String args[]) throws Exception
                            {
                                    Scanner cin=new Scanner(System.in);
                                    int a=cin.nextInt(),b=cin.nextInt();
                //                    System.out.println("结果:"+a+b);
                                System.out.println("中文" + args[0]);
                            }
                }
                """;
        String language = "java";
        List<String> inputList = List.of("1 2", "2 3");
        ExecuteCodeRequest request = new ExecuteCodeRequest(inputList, code, language,1000);
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(request);
        System.out.println(executeCodeResponse);
    }

}
