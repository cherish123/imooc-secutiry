package com.imooc.core.validate.code.sms;


import com.imooc.core.validate.code.sms.SmsCodeSender;

//@Component("smsCodeSender")
public class DefaultSmsCodeSender implements SmsCodeSender {
    @Override
    public void send(String mobile, String code) {
        System.out.println("向手机"+mobile+"发送验证码"+code);
    }
}
