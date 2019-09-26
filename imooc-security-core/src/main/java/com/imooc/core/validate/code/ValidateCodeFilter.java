package com.imooc.core.validate.code;

import com.imooc.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private SecurityProperties securityProperties;

    //存放所有需要校验验证码的url
    private Set<String> urls= new HashSet<>();

    //验证请求url与配置的url是否匹配的工具类
    private AntPathMatcher pathMatcher = new AntPathMatcher();
    /**
     * 设置需要使用验证的url
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String yamlUrls = securityProperties.getCode().getImage().getUrl();
        if (StringUtils.isNotEmpty(yamlUrls)) {
            String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(yamlUrls, ",");
            Collections.addAll(urls, configUrls);
        }
        urls.add("/authentication/form");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        boolean action = false;
        for (String url:urls) {
            if(pathMatcher.match(url,request.getRequestURI())) {
                action = true;
                break;
            }
        }

        if (action) {
            try {
                //如果是权限控制入口进入，则进行验证码校验
                validate(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                //验证码验证失败就不再进行UsernamePasswordAuthenticationFilter里面去验证
                return;
            }
        }
        filterChain.doFilter(request,response);
    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request,ValidateCodeController.SESSION_KEY);

        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),"imageCode");

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }

        if (codeInSession.isExpired()) {
            sessionStrategy.removeAttribute(request,ValidateCodeController.SESSION_KEY);
            throw new ValidateCodeException("验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(),codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }

        sessionStrategy.removeAttribute(request,ValidateCodeController.SESSION_KEY );
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
