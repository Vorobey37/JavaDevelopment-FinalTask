package ru.gb.signingupforacarservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.signingupforacarservice.model.Car;
import ru.gb.signingupforacarservice.service.CarService;

/**
 * Контроллер для выполнения операций (указанными в классе AController) с объектами класса Car
 */
@RestController
@RequestMapping("car")
public class CarController extends AController<CarService, Car>{

    @Autowired
    public CarController(CarService service) {
        super(service);
    }
}