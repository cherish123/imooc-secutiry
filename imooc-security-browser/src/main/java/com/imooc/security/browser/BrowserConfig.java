package com.imooc.security.browser;

import com.imooc.core.authentication.AbstractChannelSecurityConfig;
import com.imooc.core.config.SmsValidateSecurityConfigure;
import com.imooc.core.config.ValidateCodeSecurityConfig;
import com.imooc.core.properties.SecurityProperties;
import com.imooc.core.validate.code.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.sql.DataSource;

@Configuration
public class BrowserConfig extends AbstractChannelSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /*@Autowired
    private AuthenticationSuccessHandler asusAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler asusAuthenticationFailureHandler;*/



//    @Autowired
//    private DataSource dataSource;

//    @Autowired
//    private DruidDataSource druidDataSource;


    @Autowired
    private SmsValidateSecurityConfigure smsValidateSecurityConfigure;

    @Autowired
    private ValidateCodeSecurityConfig validatorCodeSecurityConfig;

//    @Bean
//    public PersistentTokenRepository persistentTokenRepository() {
//        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
//        tokenRepository.setDataSource(dataSource);
////        tokenRepository.setDataSource(druidDataSource);
////        tokenRepository.setCreateTableOnStartup(true);
//        return tokenRepository;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        applyPasswordAuthenticationConfig(http);
       /* ValidatorCodeFilter validatorCodeFilter = new ValidatorCodeFilter();
        validatorCodeFilter.setAuthenticationFailureHandler(asusAuthenticationFailureHandler);
        validatorCodeFilter.setSecurityProperties(securityProperties);
        validatorCodeFilter.afterPropertiesSet();

        SmsValidatorCodeFilter smsValidatorCodeFilter = new SmsValidatorCodeFilter();
        smsValidatorCodeFilter.setAuthenticationFailureHandler(asusAuthenticationFailureHandler);
        smsValidatorCodeFilter.setSecurityProperties(securityProperties);
        smsValidatorCodeFilter.afterPropertiesSet();*/

//        http.httpBasic()
//        http.addFilterBefore(smsValidatorCodeFilter,UsernamePasswordAuthenticationFilter.class)
//            .addFilterBefore(validatorCodeFilter, UsernamePasswordAuthenticationFilter.class)
        http.apply(validatorCodeSecurityConfig)
                .and()
                .apply(smsValidateSecurityConfigure)
                .and()
//            .rememberMe()
//                .tokenRepository(persistentTokenRepository())
//                .tokenValiditySeconds(securityProperties.getBrowser().getTokenExpire())
//                .userDetailsService(userDetailsService)
//                .and()
                .authorizeRequests()
                .antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL, securityProperties.getBrowser().getLoginPage(), SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();

    }
}
