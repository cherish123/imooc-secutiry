package com.imooc.core.config;

import com.imooc.core.validate.code.sms.SmsAuthenticationFilter;
import com.imooc.core.validate.code.sms.SmsAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class SmsValidateSecurityConfigure extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationFailureHandler imoocAuthenticationFailureHandler;

    @Autowired
    private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        SmsAuthenticationFilter smsAuthenticationFilter = new SmsAuthenticationFilter();
        smsAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        smsAuthenticationFilter.setAuthenticationSuccessHandler(imoocAuthenticationSuccessHandler);
        smsAuthenticationFilter.setAuthenticationFailureHandler(imoocAuthenticationFailureHandler);

        SmsAuthenticationProvider smsAuthenticationProvider = new SmsAuthenticationProvider();
        smsAuthenticationProvider.setUserDetailsService(userDetailsService);

        http.authenticationProvider(smsAuthenticationProvider)
                .addFilterAfter(smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }


}
