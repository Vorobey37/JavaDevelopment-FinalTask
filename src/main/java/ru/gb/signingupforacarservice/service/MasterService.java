package ru.gb.signingupforacarservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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
        return !repository.findMasterByLastNameAndFistNameAndMiddleNameAndSpecialization(
                requestObject.getLastName(),
                requestObject.getFistName(),
                requestObject.getMiddleName(),
                requestObject.getSpecialization()
        ).isEmpty();
    }

    @Override
    public Master update(long id, Master requestObject) {
        checkExistsIdEntityInDB(id);
        checkRequest(requestObject);

        if(checkExistsEntityInDB(requestObject)){
            List<Master> masters = repository.findMasterByLastNameAndFistNameAndMiddleNameAndSpecialization(
                    requestObject.getLastName(),
                    requestObject.getFistName(),
                    requestObject.getMiddleName(),
                    requestObject.getSpecialization()
            );
            int count = 0;
            for (Master master : masters) {
                if (master.getId() == id){
                    count++;
                }
            }
            if (count == 0){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Мастер не уникален!");
            }
        }
        requestObject.setId(id);

        return repository.save(requestObject);
    }
}
