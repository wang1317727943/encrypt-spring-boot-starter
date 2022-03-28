package com.teai.encryptstarter.adapter;

import com.teai.encryptstarter.config.EncryptProperties;

import com.teai.encryptstarter.exception.DecryptException;
import com.teai.encryptstarter.util.AesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName DecryptRequest
 * @description POST 接口参数解密
 * @Author wangll
 * @Date 2022/2/10 15:50
 * @Version 1.0
 **/

@ControllerAdvice
public class DecryptRequest extends RequestBodyAdviceAdapter {
    @Autowired
    private EncryptProperties encryptProperties;

    Logger logger = LoggerFactory.getLogger(DecryptRequest.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasMethodAnnotation(PostMapping.class) || methodParameter.hasParameterAnnotation(RequestBody.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(final HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        if (encryptProperties.isEnabled()) {
            HttpHeaders headers = inputMessage.getHeaders();
//            Content-Length
            long contentLength = headers.getContentLength();
            int available = inputMessage.getBody().available();
            logger.info("inputMessage.getBody.length："+available);
            logger.info("Content-Length："+ contentLength );
            if (contentLength!=available){
                throw new DecryptException("网络不稳定，请重试");
            }

            byte[] body = new byte[available];
            inputMessage.getBody().read(body);

            try {
                String bodyStr = new String(body, StandardCharsets.UTF_8);
//                String bodyStr =StreamUtils.copyToString(inputMessage.getBody(), StandardCharsets.UTF_8);
                logger.info("进入加密拦截器：");
                logger.info("body字符串解密前：");
                logger.info(bodyStr);
                logger.info("解密开始：");
                byte[] decryptByte = AesUtil.decrypt2Byte(bodyStr,encryptProperties.getKey().getBytes());

                logger.info("body字符串解密后：");
                String encodestr = new String(decryptByte, StandardCharsets.UTF_8);
                logger.info(encodestr);
                logger.info("进入拦截器：解密结束。");

                final ByteArrayInputStream bais = new ByteArrayInputStream(decryptByte);

                return new HttpInputMessage() {
                    @Override
                    public InputStream getBody() throws IOException {
                        return bais;
                    }

                    @Override
                    public HttpHeaders getHeaders() {
                        return inputMessage.getHeaders();
                    }
                };
            } catch (Exception e) {
                e.printStackTrace();
            }
            return super.beforeBodyRead(inputMessage, parameter, targetType, converterType);
        }

        return inputMessage;
    }
}

