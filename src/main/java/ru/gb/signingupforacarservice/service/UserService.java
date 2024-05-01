package ru.gb.signingupforacarservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.gb.signingupforacarservice.model.AuthorizedUser;
import ru.gb.signingupforacarservice.repository.UserRepository;

import java.util.List;

@Service
public class UserService extends AService<AuthorizedUser, UserRepository>{
    @Autowired
    public UserService(UserRepository repository) {
        super(repository);
    }

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

    @Override
    public AuthorizedUser create(AuthorizedUser requestObject) {
        return super.create(requestObject);
    }

    @Override
    protected boolean checkExistsEntityInDB(AuthorizedUser requestObject) {
        return !repository.findAuthorizedUserByLogin(requestObject.getLogin()).isEmpty();
    }

    @Override
    protected List<AuthorizedUser> findByCustomFields(AuthorizedUser requestObject) {
        return repository.findAuthorizedUserByLogin(requestObject.getLogin());
    }

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
