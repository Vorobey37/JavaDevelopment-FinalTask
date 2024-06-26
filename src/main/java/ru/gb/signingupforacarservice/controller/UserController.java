package ru.gb.signingupforacarservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.signingupforacarservice.model.AuthorizedUser;
import ru.gb.signingupforacarservice.service.UserService;

/**
 * Контроллер для выполнения операций (указанными в классе AController) с объектами класса AuthorizedUser
 */
@RestController
@RequestMapping("user")
public class UserController extends AController<UserService, AuthorizedUser>{

    @Autowired
    public UserController(UserService service) {
        super(service);
    }
}
