package com.imooc.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.imooc.dto.User;
import com.imooc.dto.UserQueryCondition;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @RequestMapping(value = "/user",method = RequestMethod.GET)
//    public List<User> query(@RequestParam String username) {
    @JsonView(User.UserSimpleView.class)
    public List<User> query(@Valid UserQueryCondition condition, BindingResult errors) {

        if (errors.hasErrors()){
            errors.getAllErrors().forEach(error -> {
                FieldError fieldError = (FieldError) error;
                String message = fieldError.getField()+" |"+error.getDefaultMessage();
                System.out.println(message);
            });
        }

        System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));

        ArrayList<User> users = new ArrayList<User>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }
}
