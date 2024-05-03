package ru.gb.signingupforacarservice.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Управление паролями в приложении
 */
@Component
public class CustomPasswordEncoder implements PasswordEncoder {
    /**
     * Кодирование, предоставленного пользователем пароля
     * @param rawPassword пароль клиента
     * @return закодированный пароль
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    /**
     * Сопоставление предоставленного пользователем пароля с паролем из хранилища
     * @param rawPassword пароль клиента
     * @param encodedPassword пароль из хранилища
     * @return результат сравнения
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
