package com.ydhl.outsourcing.ts.finance.service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Junpeng.Su
 * @create 2017-08-16 下午 5:33
 * @description
 */
@Service("passwordEncoder")
public class PasswordEncoderImpl implements PasswordEncoder, InitializingBean {

    /**
     * 密码加密器
     */
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void afterPropertiesSet() throws Exception {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
