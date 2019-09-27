package com.imooc.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@RestController
public class ValidateCodeController {

    static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    @Autowired
    private ValidateCodeGenerator smsCodeGenerator;

    @Autowired
    private SmsCodeSender smsCodeSender;

    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //  1.根据随机数生成图片
        ImageCode imageCode = (ImageCode)imageCodeGenerator.generate(new ServletWebRequest(request));
        // 2.将随机数存到Session中
        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,imageCode);
        // 3.将生成的图片写到接口的响应中
        ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
    }

    /**
     * 短信验证码生成
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/code/sms")
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //  1.生成短信验证码
        ValidateCode smsCode = smsCodeGenerator.generate(new ServletWebRequest(request));
        // 2.将随机数存到Session中
        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,smsCode);
        // 3.接入短信服务运营商
        String mobile = ServletRequestUtils.getRequiredStringParameter(request,"mobile");
        smsCodeSender.send(mobile,smsCode.getCode());
    }
}
