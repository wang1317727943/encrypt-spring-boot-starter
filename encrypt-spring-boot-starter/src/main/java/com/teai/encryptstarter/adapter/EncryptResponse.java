package com.teai.encryptstarter.adapter;

import com.alibaba.fastjson.JSON;
import com.teai.encryptstarter.config.EncryptProperties;

import com.teai.encryptstarter.result.ResultInfo;
import com.teai.encryptstarter.util.AesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @ClassName EncryptResponse
 * @description GetMapping接口返回数据加密
 * @Author wangll
 * @Date 2022/2/10 14:29
 * @Version 1.0
 **/

@ControllerAdvice
public class EncryptResponse implements ResponseBodyAdvice<ResultInfo> {

    @Autowired
    private EncryptProperties encryptProperties;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.hasMethodAnnotation(GetMapping.class);
    }

    @Override
    public ResultInfo beforeBodyWrite(ResultInfo body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (encryptProperties.isEnabled()){
            try {
                /*if (body.getMessage()!=null) {
                    body.setMessage(AesUtil.encrypt(body.getMessage()));
                }*/
                if (body.getData() != null) {
//                body.setData(AESUtils.encrypt(om.writeValueAsBytes(body.getData()), keyBytes));
                    body.setData(AesUtil.encrypt(JSON.toJSONString(body.getData()),encryptProperties.getKey().getBytes()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return body;
    }
}
