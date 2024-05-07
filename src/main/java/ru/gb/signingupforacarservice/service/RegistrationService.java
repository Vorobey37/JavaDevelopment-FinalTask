package ru.gb.signingupforacarservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.gb.signingupforacarservice.model.*;
import ru.gb.signingupforacarservice.repository.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Сервис для взаимодействия с записями в автосервис
 */
@Service
public class RegistrationService extends AService<Registration, RegistrationRepository>{

    private final ClientRepository clientRepository;
    private final CarRepository carRepository;
    private final MasterRepository masterRepository;
    private final RepairTypeRepository repairTypeRepository;

    @Autowired
    public RegistrationService(RegistrationRepository repository, ClientRepository clientRepository, CarRepository carRepository, MasterRepository masterRepository, RepairTypeRepository repairTypeRepository) {
        super(repository);
        this.clientRepository = clientRepository;
        this.carRepository = carRepository;
        this.masterRepository = masterRepository;
        this.repairTypeRepository = repairTypeRepository;
    }

    /**
     * Создание записи в автосервис
     * @param requestObject Передаваемая запись для создания
     * @return Созданная запись
     */
    @Override
    public Registration create(Registration requestObject) {
        LocalDateTime timeTo = requestObject
                .getTimeFrom()
                .plusMinutes(requestObject.getRepairType().getDurationTime());

        requestObject.setTimeTo(timeTo);

        checkRegistrationValues(requestObject);

        return super.create(requestObject);
    }

    /**
     * Редактирование записи в автосервис
     * @param id id записи, которую нужно отредактировать
     * @param requestObject Отредактированная запись
     * @return Отредактированная запись
     */
    @Override
    public Registration update(long id, Registration requestObject) {
        checkExistsIdEntityInDB(id);
        checkRequest(requestObject);

        requestObject.setId(id);

        LocalDateTime timeTo = requestObject
                .getTimeFrom()
                .plusMinutes(requestObject.getRepairType().getDurationTime());

        requestObject.setTimeTo(timeTo);

        checkRegistrationValues(requestObject);

        return repository.save(requestObject);
    }

    /**
     * Поиск всех записей по конкретному мастеру
     * @param masterId id мастера
     * @return Список записей
     */
    public List<Registration> findAllByMaster(long masterId){
        Master master = masterRepository.findById(masterId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Мастер не найден по id: " + masterId)
                );

        return repository.findRegistrationByMaster(master);
    }

    /**
     * Поиск всех записей по конкретному клиенту
     * @param clientId id клиента
     * @return Список записей
     */
    public List<Registration> findAllByClient(long clientId){
        CarServiceClient client = clientRepository.findById(clientId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Клиент не найден по id: " + clientId)
                );

        return repository.findRegistrationByCarServiceClient(client);
    }

    /**
     * Поиск всех записей по конкретному автомобилю
     * @param carId id автомобиля
     * @return Список записей
     */
    public List<Registration> findAllByCar(long carId){
        Car car = carRepository.findById(carId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Мастер не найден по id: " + carId)
                );

        return repository.findRegistrationByCar(car);
    }

    /**
     * Поиск будующих записей по конкретному мастеру
     * @param masterId id мастера
     * @return Список записей
     */
    public List<Registration> findActualByMaster(long masterId){
        List<Registration> registrations = findAllByMaster(masterId);

        return registrations.stream()
                .filter(reg -> reg.getTimeFrom().isAfter(LocalDateTime.now()))
                .toList();
    }

    /**
     * Поиск будующих записей по конкретному автомобилю
     * @param carId id автомобиля
     * @return Список записей
     */
    public List<Registration> findActualByCar(long carId){
        List<Registration> registrations = findAllByCar(carId);

        return registrations.stream()
                .filter(reg -> reg.getTimeFrom().isAfter(LocalDateTime.now()))
                .toList();
    }

    /**
     * Поиск будующих записей по конкретному клиенту
     * @param clientId id клиента
     * @return Список записей
     */
    public List<Registration> findActualByClient(long clientId){
        List<Registration> registrations = findAllByClient(clientId);

        return registrations.stream()
                .filter(reg -> reg.getTimeFrom().isAfter(LocalDateTime.now()))
                .toList();
    }

    /**
     * Проверка полей записи в автосервис
     * @param requestObject Передаваемая запись для проверки
     */
    private void checkRegistrationValues(Registration requestObject){
        checkClientValue(requestObject.getCarServiceClient());
        checkCarValue(requestObject.getCar());
        checkMasterValue(requestObject.getMaster());
        checkRepairTypeValue(requestObject.getRepairType());
        checkTimeFromValue(requestObject.getTimeFrom());
        checkConflictMasterTime(requestObject);
    }

    /**
     * Проверка поля клиент в записи на сервис
     * @param carServiceClient Передаваемый клиент для проверки
     */
    private void checkClientValue(CarServiceClient carServiceClient){
        if (carServiceClient == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Клиент не может быть null!");
        }
        if (!clientRepository.existsById(carServiceClient.getId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Клиент не существует с id: " + carServiceClient.getId());
        }
    }

    /**
     * Проверка поля автомобиль в записи на сервис
     * @param car Передаваемый автомобиль для проверки
     */
    private void checkCarValue(Car car){
        if (car == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Автомобиль не может быть null!");
        }
        if (!carRepository.existsById(car.getId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Автомобиль не существует с id: " + car.getId());
        }
    }

    /**
     * Проверка поля мастер в записи на сервис
     * @param master Передаваемый мастер для проверки
     */
    private void checkMasterValue(Master master){
        if (master == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Мастер не может быть null!");
        }
        if (!masterRepository.existsById(master.getId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Мастер не существует с id: " + master.getId());
        }
    }

    /**
     * Проверка поля вид ремонта в записи на сервис
     * @param repairType Передаваемый вид ремонта для проверки
     */
    private void checkRepairTypeValue(RepairType repairType){
        if (repairType == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Вид ремонта не может быть null!");
        }
        if (!repairTypeRepository.existsById(repairType.getId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Вид ремонта не существует с id: " + repairType.getId());
        }
    }

    /**
     * Проверка времени начала ремонта в записи на сервис
     * @param timeFrom Передаваемое время для проверки
     */
    private void checkTimeFromValue(LocalDateTime timeFrom){
        if (timeFrom == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Время начала ремонта не может быть null!");
        }
        if (timeFrom.isBefore(LocalDateTime.now())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Время начала ремонта не может быть раньше текущей даты!");
        }
    }

    /**
     * Проверка загрузки мастера в данное время
     * @param requestObject Передаваемая запись для проверки
     */
    private void checkConflictMasterTime(Registration requestObject){
        List<Registration> registrations = repository.findRegistrationByMaster(requestObject.getMaster());

        for (Registration element : registrations) {
            boolean isTimesFromEquals =
                    requestObject.getTimeFrom().equals(element.getTimeFrom());

            boolean isTimesToEquals =
                    requestObject.getTimeTo().equals(element.getTimeTo());

            boolean isTimeFromExistsMasterTime =
                    requestObject.getTimeFrom().isAfter(element.getTimeFrom())
                            && requestObject.getTimeFrom().isBefore(element.getTimeTo());

            boolean isTimeToExistsMasterTime =
                    requestObject.getTimeTo().isAfter(element.getTimeFrom())
                            && requestObject.getTimeTo().isBefore(element.getTimeTo());

            boolean isMasterTimeExistsRequestTime =
                    element.getTimeFrom().isAfter(requestObject.getTimeFrom())
                            && element.getTimeTo().isBefore(requestObject.getTimeTo());

            if (isTimesFromEquals
                    || isTimesToEquals
                    || isTimeFromExistsMasterTime
                    || isTimeToExistsMasterTime
                    || isMasterTimeExistsRequestTime){

                if (!Objects.equals(requestObject.getId(), element.getId())){
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Желаемый мастер занят в это время");
                }
            }
        }
    }

}
