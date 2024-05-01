package ru.gb.signingupforacarservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.signingupforacarservice.model.Car;
import ru.gb.signingupforacarservice.repository.CarRepository;

import java.util.List;

@Service
public class CarService extends AService<Car, CarRepository>{

    @Autowired
    public CarService(CarRepository repository) {
        super(repository);
    }

    @Override
    protected boolean checkExistsEntityInDB(Car requestObject) {
        return !findByCustomFields(requestObject).isEmpty();
    }

    @Override
    protected List<Car> findByCustomFields(Car requestObject) {
        return repository.findCarByVIN(requestObject.getVIN());
    }
}
