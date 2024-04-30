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

@RestController
@RequestMapping("registration")
public class RegistrationController extends AController<RegistrationService, Registration> {
    @Autowired
    public RegistrationController(RegistrationService service) {
        super(service);
    }

    @GetMapping("master/{masterId}")
    public ResponseEntity<List<Registration>> getAllByMaster(@PathVariable long masterId){
        return ResponseEntity.ok().body(service.findAllByMaster(masterId));
    }

    @GetMapping("client/{clientId}")
    public ResponseEntity<List<Registration>> getAllByClient(@PathVariable long clientId){
        return ResponseEntity.ok().body(service.findAllByClient(clientId));
    }

    @GetMapping("car/{carId}")
    public ResponseEntity<List<Registration>> getAllByCar(@PathVariable long carId){
        return ResponseEntity.ok().body(service.findAllByCar(carId));
    }

    @GetMapping("master/actual/{masterId}")
    public ResponseEntity<List<Registration>> getActualByMaster(@PathVariable long masterId){
        return ResponseEntity.ok().body(service.findActualByMaster(masterId));
    }

    @GetMapping("car/actual/{carId}")
    public ResponseEntity<List<Registration>> getActualByCar(@PathVariable long carId){
        return ResponseEntity.ok().body(service.findActualByCar(carId));
    }

    @GetMapping("client/actual/{clientId}")
    public ResponseEntity<List<Registration>> getActualByClient(@PathVariable long clientId){
        return ResponseEntity.ok().body(service.findActualByClient(clientId));
    }
}
