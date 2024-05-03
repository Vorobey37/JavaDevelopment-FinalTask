package ru.gb.signingupforacarservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.signingupforacarservice.model.Car;
import ru.gb.signingupforacarservice.model.CarServiceClient;
import ru.gb.signingupforacarservice.model.Master;
import ru.gb.signingupforacarservice.model.Registration;

import java.util.List;

/**
 * Интерфейс для взаимодействия с записями на сервис, занесенными в БД
 */
@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findRegistrationByMaster(Master master);
    List<Registration> findRegistrationByCar(Car car);
    List<Registration> findRegistrationByCarServiceClient(CarServiceClient carServiceClient);
}
