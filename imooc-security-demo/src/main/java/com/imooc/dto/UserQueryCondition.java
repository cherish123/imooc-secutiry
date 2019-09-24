package com.imooc.dto;

import com.imooc.annotation.Check;

public class UserQueryCondition {

    private String name;

    private int age;

    /**
     * 性别 man or women
     * */
    @Check(paramValues = {"man", "woman"},message = "性别只能是男或者女")
    private String sex;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
