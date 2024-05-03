package ru.gb.signingupforacarservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.signingupforacarservice.model.Registration;
import ru.gb.signingupforacarservice.service.RegistrationService;

import java.util.List;

/**
 * Контроллер для выполнения операций с объектами класса Registration, в том числе с теми, которые указанны в классе AController
 */
@RestController
@RequestMapping("registration")
public class RegistrationController extends AController<RegistrationService, Registration> {

    @Autowired
    public RegistrationController(RegistrationService service) {
        super(service);
    }

    /**
     * Получение списка всех записей по конкретному мастеру в теле http-ответа
     * @param masterId id мастера
     * @return список записей в теле http-ответа
     */
    @GetMapping("master/{masterId}")
    public ResponseEntity<List<Registration>> getAllByMaster(@PathVariable long masterId){
        return ResponseEntity.ok().body(service.findAllByMaster(masterId));
    }

    /**
     * Получение списка всех записей по конкретному клиенту в теле http-ответа
     * @param clientId id клиента
     * @return список записей в теле http-ответа
     */
    @GetMapping("client/{clientId}")
    public ResponseEntity<List<Registration>> getAllByClient(@PathVariable long clientId){
        return ResponseEntity.ok().body(service.findAllByClient(clientId));
    }

    /**
     * Получение списка всех записей по конкретному автомобилю в теле http-ответа
     * @param carId id автомобиля
     * @return список записей в теле http-ответа
     */
    @GetMapping("car/{carId}")
    public ResponseEntity<List<Registration>> getAllByCar(@PathVariable long carId){
        return ResponseEntity.ok().body(service.findAllByCar(carId));
    }

    /**
     * Получение списка всех будующих записей по конкретному мастеру в теле http-ответа
     * @param masterId id мастера
     * @return список записей в теле http-ответа
     */
    @GetMapping("master/actual/{masterId}")
    public ResponseEntity<List<Registration>> getActualByMaster(@PathVariable long masterId){
        return ResponseEntity.ok().body(service.findActualByMaster(masterId));
    }

    /**
     * Получение списка всех будующих записей по конкретному автомобилю в теле http-ответа
     * @param carId id автомобиля
     * @return список записей в теле http-ответа
     */
    @GetMapping("car/actual/{carId}")
    public ResponseEntity<List<Registration>> getActualByCar(@PathVariable long carId){
        return ResponseEntity.ok().body(service.findActualByCar(carId));
    }

    /**
     * Получение списка всех будующих записей по конкретному мастеру в теле http-ответа
     * @param clientId id клиента
     * @return список записей в теле http-ответа
     */
    @GetMapping("client/actual/{clientId}")
    public ResponseEntity<List<Registration>> getActualByClient(@PathVariable long clientId){
        return ResponseEntity.ok().body(service.findActualByClient(clientId));
    }
}
