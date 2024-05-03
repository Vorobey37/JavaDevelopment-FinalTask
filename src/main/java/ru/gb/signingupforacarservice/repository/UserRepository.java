package ru.gb.signingupforacarservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.signingupforacarservice.model.AuthorizedUser;
import java.util.List;

/**
 * Интерфейс для взаимодействия с авторизованными пользователями, занесенными в БД
 */
@Repository
public interface UserRepository extends JpaRepository<AuthorizedUser, Long> {
    List<AuthorizedUser> findAuthorizedUserByLogin(String login);
}
