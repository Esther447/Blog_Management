package com.example.managementblog.Postgres.service;

import javax.naming.AuthenticationException;

public class usernameNotFoundException extends org.springframework.security.core.userdetails.UsernameNotFoundException {
    private static final long serialVersionUID = 1L;

    public usernameNotFoundException(String msg) {
        super(msg);
    }

    public usernameNotFoundException(String msg, Throwable cause) { super(msg, cause);
    }
}
