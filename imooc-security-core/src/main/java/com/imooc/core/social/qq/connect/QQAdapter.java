package com.imooc.core.social.qq.connect;

import com.imooc.core.social.qq.api.QQ;
import com.imooc.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

public class QQAdapter implements ApiAdapter<QQ> {

    /**
     * 判断QQAPI是否是可用的，QQ服务时否是通的
     * @param api
     * @return
     */
    @Override
    public boolean test(QQ api) {
        //不发请求去判断，默认是好的
        return true;
    }

    /**
     *在connection的数据和api的数据之间做适配
     * @param api
     * @param values
     */
    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo userInfo = api.getUserInfo();

        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        values.setProfileUrl(null);//QQ没有个人主页
        values.setProviderUserId(userInfo.getOpenId());

    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    @Override
    public void updateStatus(QQ api, String message) {

    }
}
