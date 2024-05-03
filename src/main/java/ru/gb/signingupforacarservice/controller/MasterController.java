package ru.gb.signingupforacarservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.signingupforacarservice.model.Master;
import ru.gb.signingupforacarservice.service.MasterService;

/**
 * Контроллер для выполнения операций (указанными в классе AController) с объектами класса Master
 */
@RestController
@RequestMapping("master")
public class MasterController extends AController<MasterService, Master>{

    @Autowired
    public MasterController(MasterService service) {
        super(service);
    }
}
