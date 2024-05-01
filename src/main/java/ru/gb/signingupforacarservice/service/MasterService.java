package ru.gb.signingupforacarservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.signingupforacarservice.model.Master;
import ru.gb.signingupforacarservice.repository.MasterRepository;

import java.util.List;

@Service
public class MasterService extends AService<Master, MasterRepository>{

    @Autowired
    public MasterService(MasterRepository repository) {
        super(repository);
    }

    @Override
    protected boolean checkExistsEntityInDB(Master requestObject) {
        return !findByCustomFields(requestObject).isEmpty();
    }

    @Override
    protected List<Master> findByCustomFields(Master requestObject) {
        return repository.findMasterByLastNameAndFistNameAndMiddleNameAndSpecialization(
                requestObject.getLastName(),
                requestObject.getFistName(),
                requestObject.getMiddleName(),
                requestObject.getSpecialization()
        );
    }
}
