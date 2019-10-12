package com.imooc.core.social.qq.api;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;


/**
 * 这是一个多实例的，每一个用户授权时就会产生一份，不能用@Component（否则全局变量accessToken会有线程安全问题）
 *
 *
 * qq服务提供商实现api用于获取用户信息
 * 调用qq的openApi需要三个参数
 * access_token：可通过使用Authorization_Code获取Access_Token 或来获取。access_token有3个月有效期。
 * oauth_consumer_key：	申请QQ登录成功后，分配给应用的appid
 * openid：	用户的ID，与QQ号码一一对应。可通过调用https://graph.qq.com/oauth2.0/me?access_token=YOUR_ACCESS_TOKEN 来获取。
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    private Logger log = LoggerFactory.getLogger(getClass());

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;

    private String openId;

    public QQImpl(String accessToken,String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);//会将accessToken串到请求上
        this.appId = appId;

        String url = String.format(URL_GET_OPENID,accessToken);
        String result = getRestTemplate().getForObject(url,String.class);
        log.info(result);//callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
        this.openId = StringUtils.substringBetween(result,"\"openid\":\"","\"}");
    }
    @Override
    public QQUserInfo getUserInfo() {
        //获取用户信息的url
        String url = String.format(URL_GET_USERINFO,appId,openId);
        //用户信息的返回
        String reponse = getRestTemplate().getForObject(url, String.class);
        log.info(reponse);
        QQUserInfo userInfo = null;
        try {
            userInfo = objectMapper.readValue(reponse,QQUserInfo.class);
            userInfo.setOpenId(openId);
            return userInfo;
        } catch (IOException e) {
            throw new RuntimeException("获取用户信息失败");
        }
    }
}
