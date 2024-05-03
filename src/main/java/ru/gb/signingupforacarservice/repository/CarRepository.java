package ru.gb.signingupforacarservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.signingupforacarservice.model.Car;

import java.util.List;

/**
 * Интерфейс для взаимодействия с автомобилями, занесенными в БД
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findCarByVIN(String VIN);

}
