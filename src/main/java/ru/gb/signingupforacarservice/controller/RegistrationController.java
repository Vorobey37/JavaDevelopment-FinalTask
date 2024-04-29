package ru.gb.signingupforacarservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.signingupforacarservice.model.Registration;
import ru.gb.signingupforacarservice.service.RegistrationService;

@RestController
@RequestMapping("registration")
public class RegistrationController extends AController<RegistrationService, Registration> {
    @Autowired
    public RegistrationController(RegistrationService service) {
        super(service);
    }
}
