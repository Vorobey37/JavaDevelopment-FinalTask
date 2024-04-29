package ru.gb.signingupforacarservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.signingupforacarservice.model.RepairType;
import ru.gb.signingupforacarservice.service.RepairTypeService;

@RestController
@RequestMapping("repair")
public class RepairTypeController extends AController<RepairTypeService, RepairType>{

    @Autowired
    public RepairTypeController(RepairTypeService service) {
        super(service);
    }
}
