package ru.gb.signingupforacarservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.signingupforacarservice.model.CarServiceClient;

/**
 * Интерфейс для взаимодействия с клиентами, занесенными в БД
 */
@Repository
public interface ClientRepository extends JpaRepository<CarServiceClient, Long> {

}
