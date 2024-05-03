package ru.gb.signingupforacarservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.signingupforacarservice.model.CarServiceClient;
import ru.gb.signingupforacarservice.repository.ClientRepository;

/**
 * Сервис для взаимодействия с клиентами
 */
@Service
public class ClientService extends AService<CarServiceClient, ClientRepository> {
    @Autowired
    public ClientService(ClientRepository repository) {
        super(repository);
    }

}
