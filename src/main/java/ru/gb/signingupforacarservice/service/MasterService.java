package ru.gb.signingupforacarservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.signingupforacarservice.model.Master;
import ru.gb.signingupforacarservice.repository.MasterRepository;

import java.util.List;

/**
 * Сервис для взаимодействия с мастерами
 */
@Service
public class MasterService extends AService<Master, MasterRepository>{

    @Autowired
    public MasterService(MasterRepository repository) {
        super(repository);
    }

    /**
     * Проверка существования мастера в БД
     * @param requestObject Передаваемый мастер для проверки
     * @return Логический результат проверки
     */
    @Override
    protected boolean checkExistsEntityInDB(Master requestObject) {
        return !findByCustomFields(requestObject).isEmpty();
    }

    /**
     * Поиск мастера по ФИО и должности по VIN в БД
     * @param requestObject Передаваемый мастер для поиска
     * @return Список найденных мастеров
     */
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
