package com.imooc.core.validate.code;

import com.imooc.core.properties.SecurityProperties;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String smsCode = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new ValidateCode(smsCode,securityProperties.getCode().getSms().getExpireIn());
    }

}
