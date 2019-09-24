package com.imooc.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.imooc.annotation.Check;

public class User {

    //定义简单视图
    public interface UserSimpleView{};
    //定义复杂视图
    public interface UserDetailView extends UserSimpleView{};


    private String username;

    private String password;

    /**
     * 性别 man or women
     * */
    @Check(paramValues = {"man", "woman"})
    private String sex;

    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @JsonView(UserDetailView.class)
    public void setPassword(String password) {

        this.password = password;
    }

    @JsonView(UserSimpleView.class)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
