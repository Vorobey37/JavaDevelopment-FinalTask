package ru.gb.signingupforacarservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.signingupforacarservice.model.RepairType;
import ru.gb.signingupforacarservice.repository.RepairTypeRepository;

@Service
public class RepairTypeService extends AService<RepairType, RepairTypeRepository>{

    @Autowired
    public RepairTypeService(RepairTypeRepository repository) {
        super(repository);
    }

    @Override
    protected boolean checkExistsEntityInDB(RepairType requestObject) {
        return !repository.findRepairTypeByNameAndDurationTimeAndPrice(
                requestObject.getName(),
                requestObject.getDurationTime(),
                requestObject.getPrice()
        ).isEmpty();
    }

}
