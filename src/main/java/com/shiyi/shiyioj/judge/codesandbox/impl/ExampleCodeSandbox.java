package com.shiyi.shiyioj.judge.codesandbox.impl;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.shiyi.shiyioj.common.ErrorCode;
import com.shiyi.shiyioj.constant.CodeSandboxConstant;
import com.shiyi.shiyioj.exception.BusinessException;
import com.shiyi.shiyioj.judge.codesandbox.CodeSandbox;
import com.shiyi.shiyioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.shiyi.shiyioj.judge.codesandbox.model.ExecuteCodeResponse;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static cn.hutool.crypto.symmetric.Vigenere.encrypt;


/**
 * 演示 测试
 */
@Service
public class ExampleCodeSandbox implements CodeSandbox {

    private final OkHttpClient client = new OkHttpClient();
    private final String nativeUrl = CodeSandboxConstant.url + "/native/executeCode";
    private final String dockerUrl = CodeSandboxConstant.url + "/docker/executeCode";
    private final byte[] key = "1234567890123456".getBytes();
    AES aes = new AES(key);

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest request) {

        String jsonStr = JSONUtil.toJsonStr(request);
        String nowUrl;
        if (CodeSandboxConstant.dockerEnable) {
            nowUrl = dockerUrl;
        } else {
            nowUrl = nativeUrl;
        }

        String encryptedValue = aes.encryptHex(CodeSandboxConstant.AUTH_REQUEST_VALUE);
        System.out.println("加密后的值: " + encryptedValue);

        System.out.println("请求地址：" + nowUrl);
        Request request1 = new Request.Builder()
                .header(CodeSandboxConstant.AUTH_REQUEST_HEADER, encryptedValue)
                .url(nowUrl)
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr))
                .build();
        Call call = client.newCall(request1);
        Response response;
        try {
            response = call.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ExecuteCodeResponse bean = null;
        if (response.body() != null) {
            try {
                bean = JSONUtil.toBean(response.body().string(), ExecuteCodeResponse.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (bean == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return bean;
    }
}
