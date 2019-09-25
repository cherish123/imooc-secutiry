package com.imooc.core.validate.code;

import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = -8090099049687671179L;

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
