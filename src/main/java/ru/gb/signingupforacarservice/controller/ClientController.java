package ru.gb.signingupforacarservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gb.signingupforacarservice.model.CarServiceClient;
import ru.gb.signingupforacarservice.service.ClientService;


@RestController
@RequestMapping("client")
public class ClientController extends AController<ClientService, CarServiceClient>{

    @Autowired
    public ClientController(ClientService service) {
        super(service);
    }

}
