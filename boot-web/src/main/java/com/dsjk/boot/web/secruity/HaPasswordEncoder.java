package com.dsjk.boot.web.secruity;

import com.dsjk.boot.common.utils.Encodes;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自定义加密方式
 *
 * @author fengcheng
 * @version 2017/8/11
 */
public class HaPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return Encodes.encryptPassword(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return Encodes.validatePassword(rawPassword.toString(), encodedPassword);
    }
}
