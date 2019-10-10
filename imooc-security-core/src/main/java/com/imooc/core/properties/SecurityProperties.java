package com.imooc.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "imooc.security")
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();

    private ValidatorProperties code = new ValidatorProperties();

    private SocialProperties social = new SocialProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    public ValidatorProperties getCode() {
        return code;
    }

    public void setCode(ValidatorProperties code) {
        this.code = code;
    }

    public SocialProperties getSocial() {
        return social;
    }

    public void setSocial(SocialProperties social) {
        this.social = social;
    }
}

