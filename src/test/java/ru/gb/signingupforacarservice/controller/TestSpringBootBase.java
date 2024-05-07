package ru.gb.signingupforacarservice.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.signingupforacarservice.model.*;
import ru.gb.signingupforacarservice.repository.*;
import ru.gb.signingupforacarservice.security.TestSecurityConfig;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public abstract class TestSpringBootBase {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    RegistrationRepository registrationRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CarRepository carRepository;
    @Autowired
    MasterRepository masterRepository;
    @Autowired
    RepairTypeRepository repairTypeRepository;

    protected final CarServiceClient client1 = new CarServiceClient();
    protected final CarServiceClient client2 = new CarServiceClient();
    protected final Car car1 = new Car();
    protected final Car car2 = new Car();
    protected final Master master1 = new Master();
    protected final Master master2 = new Master();
    protected final RepairType repairType1 = new RepairType();
    protected final RepairType repairType2 = new RepairType();
    protected LocalDateTime timeFrom1;
    protected LocalDateTime timeFrom2;
    protected LocalDateTime timeTo1;
    protected LocalDateTime timeTo2;
    protected final Registration registration1 = new Registration();
    protected final Registration registration2 = new Registration();

    @BeforeEach
    void setUp(){
        client1.setFistName("Иван");
        client1.setLastName("Иванов");
        client1.setMiddleName("Иванович");
        client1.setPhoneNumber("8-921-21-21");
        client1.setEmail("Ivanov@mail.ru");

        client2.setFistName("Василий");
        client2.setLastName("Васильев");
        client2.setMiddleName("Васильевич");
        client2.setPhoneNumber("8-911-11-11");
        client2.setEmail("Vasilev@mail.ru");

        master1.setLastName("Шаповаленко");
        master1.setFistName("Артем");
        master1.setMiddleName("Викторович");
        master1.setSpecialization("Диагност");
        master1.setWorkExperience(3.5f);

        master2.setLastName("Черняков");
        master2.setFistName("Александр");
        master2.setMiddleName("Александрович");
        master2.setSpecialization("Механик");
        master2.setWorkExperience(5.5f);

        car1.setBrand("Toyota");
        car1.setModel("Camry");
        car1.setEngineType("Бензин");
        car1.setEngineVolume(2.4f);
        car1.setTransmissionType("АКПП");
        car1.setVIN("XXX999507895F5");
        car1.setStateNumber("Е802ОК35");

        car2.setBrand("Soda");
        car2.setModel("Octavia");
        car2.setEngineType("Дизель");
        car2.setEngineVolume(2.0f);
        car2.setTransmissionType("МКПП");
        car2.setVIN("XYZ9995DK0001");
        car2.setStateNumber("Е767ЕВ35");

        repairType1.setName("Диагностика системы ABS");
        repairType1.setDurationTime(180);
        repairType1.setPrice(9500);

        repairType2.setName("Замена сцепления");
        repairType2.setDurationTime(580);
        repairType2.setPrice(19500);

        timeFrom1 = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).minusMinutes(50);
        timeTo1 = timeFrom1.plusMinutes(repairType1.getDurationTime());

        timeFrom2 = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plusMinutes(50);
        timeTo2 = timeFrom1.plusMinutes(repairType1.getDurationTime());

        clientRepository.save(client1);
        carRepository.save(car1);
        masterRepository.save(master1);
        repairTypeRepository.save(repairType1);
        registrationRepository.save(registration1);

        clientRepository.save(client2);
        carRepository.save(car2);
        masterRepository.save(master2);
        repairTypeRepository.save(repairType2);
        registrationRepository.save(registration2);

        registration1.setCarServiceClient(client1);
        registration1.setCar(car1);
        registration1.setMaster(master1);
        registration1.setRepairType(repairType1);
        registration1.setTimeFrom(timeFrom1);
        registration1.setTimeTo(timeTo1);

        registration2.setCarServiceClient(client2);
        registration2.setCar(car2);
        registration2.setMaster(master2);
        registration2.setRepairType(repairType2);
        registration2.setTimeFrom(timeFrom2);
        registration2.setTimeTo(timeTo2);
    }
}
