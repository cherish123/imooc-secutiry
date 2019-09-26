package com.imooc.security.validate;

import com.imooc.core.validate.code.ImageCode;
import com.imooc.core.validate.code.ValidateCodeGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

//@Component("imageCodeGenerator")
public class ValidateCodeGeneratorDemo implements ValidateCodeGenerator {
    @Override
    public ImageCode generate(ServletWebRequest request) {
        System.out.println("更高级的图形验证码生成");
        return null;
    }
}
