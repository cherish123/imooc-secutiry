package com.imooc.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeProcessor {

    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 创造校验码
     */
    void create(ServletWebRequest request) throws Exception;


    /**
     * 验证校验码
     */
    void validate(ServletWebRequest servletWebRequest);
}