package ru.gb.signingupforacarservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.gb.signingupforacarservice.model.AuthorizedUser;
import ru.gb.signingupforacarservice.service.UserService;

import java.util.List;

/**
 * Сервис авторизации
 */
@Component
@RequiredArgsConstructor
public class AuthorizeService implements UserDetailsService {

    private final UserService userService;

    /**
     *Представление прошедшего проверку подлинности пользователя
     * @param userName login пользователя
     * @return Пользователя, прошедшего проверку
     * @throws UsernameNotFoundException исключение, дающее понять, что пользователь не найден по login
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        AuthorizedUser user = userService.findUserByLogin(userName);

        return new User(
                user.getLogin(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}
