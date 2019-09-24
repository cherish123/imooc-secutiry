package com.imooc.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * 两个泛型：
 * 要验证的注解名：Check
 * 注解放在什么字段上才会起作用：Object
 */
public class ParamConstraintValidated implements ConstraintValidator<Check, Object> {

    /**
     * 合法的参数值，从注解中获取
     * */
    private List<String> paramValues;

    @Override
    public void initialize(Check constraintAnnotation) {
        //初始化时获取注解上的值
        paramValues = Arrays.asList(constraintAnnotation.paramValues());
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return paramValues.contains(o);
    }
}
