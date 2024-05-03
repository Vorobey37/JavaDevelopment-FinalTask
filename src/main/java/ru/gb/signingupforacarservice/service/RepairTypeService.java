package ru.gb.signingupforacarservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.signingupforacarservice.model.RepairType;
import ru.gb.signingupforacarservice.repository.RepairTypeRepository;

import java.util.List;

/**
 * Сервис для взаимодействия с видами работ в автосервисе
 */
@Service
public class RepairTypeService extends AService<RepairType, RepairTypeRepository>{

    @Autowired
    public RepairTypeService(RepairTypeRepository repository) {
        super(repository);
    }

    /**
     * Проверка существования вида ремонта в БД
     * @param requestObject Передаваемый вид ремонта для проверки
     * @return Логический результат проверки
     */
    @Override
    protected boolean checkExistsEntityInDB(RepairType requestObject) {
        return !findByCustomFields(requestObject).isEmpty();
    }

    /**
     * Поиск вида ремонта по названию, времени исполнения и стоимости в БД
     * @param requestObject Передаваемый вида ремонта для поиска
     * @return Список видов ремонта
     */
    @Override
    protected List<RepairType> findByCustomFields(RepairType requestObject) {
        return repository.findRepairTypeByNameAndDurationTimeAndPrice(
                requestObject.getName(),
                requestObject.getDurationTime(),
                requestObject.getPrice()
        );
    }
}
