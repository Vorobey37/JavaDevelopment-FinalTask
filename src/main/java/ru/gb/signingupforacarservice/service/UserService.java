package ru.gb.signingupforacarservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.gb.signingupforacarservice.model.AuthorizedUser;
import ru.gb.signingupforacarservice.repository.UserRepository;

import java.util.List;

/**
 * Сервис для взаимодействия с пользователями приложения
 */
@Service
public class UserService extends AService<AuthorizedUser, UserRepository>{
    @Autowired
    public UserService(UserRepository repository) {
        super(repository);
    }

    /**
     * Создания пользователя с правами администратора
     * @return Созданный пользователь
     */
    @Bean
    public AuthorizedUser createRoot(){
        AuthorizedUser root = new AuthorizedUser();

        if (repository.findAuthorizedUserByLogin("root").isEmpty()){
            root.setLogin("root");
            root.setPassword("root");
            root.setRole("ADMIN");
        }

        return repository.save(root);
    }

    /**
     * Создание авторизованного пользователя
     * @param requestObject Передаваемый пользователь для создания
     * @return Созданный пользователь
     */
    @Override
    public AuthorizedUser create(AuthorizedUser requestObject) {
        return super.create(requestObject);
    }

    /**
     * Проверка существования пользователя в БД
     * @param requestObject Передаваемый объект-наследник класса ServiceModel для проверки
     * @return Логический результат проверки
     */
    @Override
    protected boolean checkExistsEntityInDB(AuthorizedUser requestObject) {
        return !repository.findAuthorizedUserByLogin(requestObject.getLogin()).isEmpty();
    }

    /**
     * Поиск авторизованных пользователей по login в БД
     * @param requestObject Передаваемый пользователь для поиска
     * @return Список пользователей
     */
    @Override
    protected List<AuthorizedUser> findByCustomFields(AuthorizedUser requestObject) {
        return repository.findAuthorizedUserByLogin(requestObject.getLogin());
    }

    /**
     * Поиск единственного авторизованного пользователя по login
     * @param login login пользователя для поиска
     * @return Авторизованный пользователь
     */
    public AuthorizedUser findUserByLogin(String login){
        if (login == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "login не может быть null!");
        }

        List<AuthorizedUser> users = repository.findAuthorizedUserByLogin(login);

        if (users.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь отсутствует с login: " + login);
        }
        if (users.size() > 1){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Существует несколько пользователей с login: " + login + ", обратитесь в техподдержку");
        }
        return users.get(0);
    }
}
