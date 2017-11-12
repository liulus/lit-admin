package com.github.lit.security.context;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

/**
 * User : liulu
 * Date : 2017/10/24 11:08
 * version $Id: LitPasswordEncoder.java, v 0.1 Exp $
 */
public class LitPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return Objects.equals(encode(rawPassword), encodedPassword);
    }
}
