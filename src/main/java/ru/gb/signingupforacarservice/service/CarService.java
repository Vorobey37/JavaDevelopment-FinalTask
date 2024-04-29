package ru.gb.signingupforacarservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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
        return !repository.findCarByVIN(requestObject.getVIN()).isEmpty();
    }

    @Override
    public Car update(long id, Car requestObject) {
        checkExistsIdEntityInDB(id);
        checkRequest(requestObject);

        if(checkExistsEntityInDB(requestObject)){
            List<Car> cars = repository.findCarByVIN(requestObject.getVIN());
            int count = 0;
            for (Car car : cars) {
                if (car.getId() == id){
                    count++;
                }
            }
            if (count == 0){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "VIN автомобиля не уникален!");
            }
        }
        requestObject.setId(id);

        return repository.save(requestObject);
    }
}
