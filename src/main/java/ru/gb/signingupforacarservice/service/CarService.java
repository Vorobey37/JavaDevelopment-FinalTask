package ru.gb.signingupforacarservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.signingupforacarservice.model.Car;
import ru.gb.signingupforacarservice.repository.CarRepository;

import java.util.List;

/**
 * Сервис для взаимодействия с автомобилями
 */
@Service
public class CarService extends AService<Car, CarRepository>{

    @Autowired
    public CarService(CarRepository repository) {
        super(repository);
    }

    /**
     * Проверка существования автомобиля в БД
     * @param requestObject Передаваемый автомобиль для проверки
     * @return Логический результат проверки
     */
    @Override
    protected boolean checkExistsEntityInDB(Car requestObject) {
        return !findByCustomFields(requestObject).isEmpty();
    }

    /**
     * Поиск автомобиля по VIN в БД
     * @param requestObject Передаваемый автомобиль для поиска
     * @return Список найденных автомобилей
     */
    @Override
    protected List<Car> findByCustomFields(Car requestObject) {
        return repository.findCarByVIN(requestObject.getVIN());
    }
}
