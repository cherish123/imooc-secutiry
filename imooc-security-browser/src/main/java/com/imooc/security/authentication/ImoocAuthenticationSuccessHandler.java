package com.imooc.security.authentication;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.core.properties.LoginResponseType;
import com.imooc.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("imoocAuthenticationSuccessHandler")
public class ImoocAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        log.info("登录成功");

        if(securityProperties.getBrowser().getLoginResponseType().equals(LoginResponseType.JSON)) {
            response.setContentType("application/json,charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        } else {
            //调用SavedRequestAwareAuthenticationSuccessHandler默认的登录成功的处理方式，跳转到用户的请求页面
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
