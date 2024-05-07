package ru.gb.signingupforacarservice.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import ru.gb.signingupforacarservice.model.*;

import java.time.LocalDateTime;
import java.util.List;

class RegistrationControllerTest extends TestSpringBootBase{

    /**
     * Тестирование метода получения всех записей на ремонт
     */
    @Test
    void testGetAll(){
        List<Registration> customRegistrations = registrationRepository.findAll();

        List<Registration> responseBody = webTestClient.get()
                .uri("registration/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Registration>>() {})
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(customRegistrations.size(), responseBody.size());

        for (int i = 0; i < customRegistrations.size(); i++) {
            Assertions.assertEquals(customRegistrations.get(i).getCarServiceClient(), responseBody.get(i).getCarServiceClient());
            Assertions.assertEquals(customRegistrations.get(i).getCar(), responseBody.get(i).getCar());
            Assertions.assertEquals(customRegistrations.get(i).getMaster(), responseBody.get(i).getMaster());
            Assertions.assertEquals(customRegistrations.get(i).getRepairType(), responseBody.get(i).getRepairType());
            Assertions.assertEquals(customRegistrations.get(i).getTimeFrom(), responseBody.get(i).getTimeFrom());
            Assertions.assertEquals(customRegistrations.get(i).getTimeTo(), responseBody.get(i).getTimeTo());
        }
    }

    /**
     * Позитивное тестирование метода получения записей по id
     */
    @Test
    void testGetByIdSuccess(){

        Registration newRegistration = new Registration();
        newRegistration.setCarServiceClient(client2);
        newRegistration.setCar(car1);
        newRegistration.setMaster(master2);
        newRegistration.setRepairType(repairType1);
        newRegistration.setTimeFrom(timeFrom2);
        newRegistration.setTimeTo(timeTo2);

        registrationRepository.save(newRegistration);

        Registration responseBody = webTestClient.get()
                .uri("registration/" + newRegistration.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Registration.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(responseBody.getId(), newRegistration.getId());
        Assertions.assertEquals(responseBody.getCarServiceClient(), newRegistration.getCarServiceClient());
        Assertions.assertEquals(responseBody.getCar(), newRegistration.getCar());
        Assertions.assertEquals(responseBody.getMaster(), newRegistration.getMaster());
        Assertions.assertEquals(responseBody.getRepairType(), newRegistration.getRepairType());
        Assertions.assertEquals(responseBody.getTimeFrom(), newRegistration.getTimeFrom());
        Assertions.assertEquals(responseBody.getTimeTo(), newRegistration.getTimeTo());

    }

    /**
     * Негативное тестирование метода получения записей по id
     */
    @Test
    void testGetByIdNotFound(){

        long size = registrationRepository.findAll().size();
        long notFoundId = size + 1;

        webTestClient.get()
                .uri("registration/" + notFoundId)
                .exchange()
                .expectStatus().isNotFound();

    }

    /**
     * Позитивное тестирование метода создания записи
     */
    @Test
    void testCreateRegistrationSuccess(){

        LocalDateTime timeFrom = LocalDateTime.now().plusMinutes(500000);
        registration1.setTimeFrom(timeFrom);
        registration1.setTimeTo(timeFrom.plusMinutes(registration1.getRepairType().getDurationTime()));

        Registration responseBody = webTestClient.post()
                .uri("registration")
                .bodyValue(registration1)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Registration.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(responseBody.getCarServiceClient(), registration1.getCarServiceClient());
        Assertions.assertEquals(responseBody.getCar(), registration1.getCar());
        Assertions.assertEquals(responseBody.getMaster(), registration1.getMaster());
        Assertions.assertEquals(responseBody.getRepairType(), registration1.getRepairType());
        Assertions.assertEquals(responseBody.getTimeFrom(), registration1.getTimeFrom());
        Assertions.assertEquals(responseBody.getTimeTo(), registration1.getTimeTo());

    }

    /**
     * Негативное тестирование с клиентом = null
     */
    @Test
    void testCreateBadRequestWithClient(){
        registration1.setCarServiceClient(null);

        webTestClient.post()
                .uri("registration")
                .bodyValue(registration1)
                .exchange()
                .expectStatus().isBadRequest();

    }

    /**
     * Негативное тестирование с несуществующим клиентом
     */
    @Test
    void testCreateNotFoundWithClient(){
        client1.setId(1000L);
        registration1.setCarServiceClient(client1);

        webTestClient.post()
                .uri("registration")
                .bodyValue(registration1)
                .exchange()
                .expectStatus().isNotFound();
    }

    /**
     * Негативное тестирование с автомобилем = null
     */
    @Test
    void testCreateBadRequestWithCar(){
        registration1.setCar(null);

        webTestClient.post()
                .uri("registration")
                .bodyValue(registration1)
                .exchange()
                .expectStatus().isBadRequest();

    }

    /**
     * Негативное тестирование с несуществующим автомобилем
     */
    @Test
    void testCreateNotFoundWithCar(){
        car1.setId(1000L);
        registration1.setCar(car1);

        webTestClient.post()
                .uri("registration")
                .bodyValue(registration1)
                .exchange()
                .expectStatus().isNotFound();
    }

    /**
     * Негативное тестирование с мастером = null
     */
    @Test
    void testCreateBadRequestWithMaster(){
        registration1.setMaster(null);

        webTestClient.post()
                .uri("registration")
                .bodyValue(registration1)
                .exchange()
                .expectStatus().isBadRequest();

    }

    /**
     * Негативное тестирование с несуществующим мастером
     */
    @Test
    void testCreateNotFoundWithMaster(){
        master1.setId(1000L);
        registration1.setMaster(master1);

        webTestClient.post()
                .uri("registration")
                .bodyValue(registration1)
                .exchange()
                .expectStatus().isNotFound();
    }

    /**
     * Негативное тестирование с несуществующим видом ремонта
     */
    @Test
    void testCreateNotFoundWithRepairType(){
        repairType1.setId(1000L);
        registration1.setRepairType(repairType1);

        webTestClient.post()
                .uri("registration")
                .bodyValue(registration1)
                .exchange()
                .expectStatus().isNotFound();
    }

    /**
     * Негативное тестирование с временем начала ремонта раньше текущего времени
     */
    @Test
    void testCreateBadRequestWithTimeFrom(){
        timeFrom1 = LocalDateTime.now().minusMinutes(10);
        registration1.setTimeFrom(timeFrom1);

        webTestClient.post()
                .uri("registration")
                .bodyValue(registration1)
                .exchange()
                .expectStatus().isBadRequest();
    }

    /**
     * Позитивное тестирование редактирования записи на сервис
     */
    @Test
    void testUpdateSuccess(){
        Registration updateRegistration = new Registration();
        updateRegistration.setCarServiceClient(client2);
        updateRegistration.setCar(car1);
        updateRegistration.setMaster(master2);
        updateRegistration.setRepairType(repairType1);
        updateRegistration.setTimeFrom(timeFrom2);
        updateRegistration.setTimeTo(timeFrom2.plusMinutes(repairType1.getDurationTime()));

        long testId = 1;

        Registration responseBody = webTestClient.put()
                .uri("registration/" + testId)
                .bodyValue(updateRegistration)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Registration.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(responseBody.getCarServiceClient(), updateRegistration.getCarServiceClient());
        Assertions.assertEquals(responseBody.getCar(), updateRegistration.getCar());
        Assertions.assertEquals(responseBody.getMaster(), updateRegistration.getMaster());
        Assertions.assertEquals(responseBody.getRepairType(), updateRegistration.getRepairType());
        Assertions.assertEquals(responseBody.getTimeFrom(), updateRegistration.getTimeFrom());
        Assertions.assertEquals(responseBody.getTimeTo(), updateRegistration.getTimeTo());

    }

    /**
     * Редактирование записи с несуществующим id
     */
    @Test
    void testUpdateNotFound(){
        Registration updateRegistration = new Registration();
        updateRegistration.setCarServiceClient(client2);
        updateRegistration.setCar(car1);
        updateRegistration.setMaster(master2);
        updateRegistration.setRepairType(repairType1);
        updateRegistration.setTimeFrom(timeFrom2);
        updateRegistration.setTimeTo(timeFrom2.plusMinutes(repairType1.getDurationTime()));

        long testId = 100;

        webTestClient.put()
                .uri("registration/" + testId)
                .bodyValue(updateRegistration)
                .exchange()
                .expectStatus().isNotFound();
    }

    /**
     * Позитивное тестирование метода удаления записи
     */
    @Test
    void testDeleteSuccess(){

        long testId = registration1.getId();
        long testSize = registrationRepository.findAll().size();

       webTestClient.delete()
                .uri("registration/" + testId)
                .exchange()
                .expectStatus().isOk();

       long actualSize = registrationRepository.findAll().size();

       Assertions.assertEquals(testSize-1, actualSize);

        webTestClient.get()
                .uri("registration/" + testId)
                .exchange()
                .expectStatus().isNotFound();

    }

    /**
     * Негативное тестирование метода с несуществующим id
     */
    @Test
    void testDeleteNotFound(){

        long testId = 3;

        webTestClient.delete()
                .uri("registration/" + testId)
                .exchange()
                .expectStatus().isNotFound();
    }

    /**
     * Тест поиска всех записей по мастеру
     */
    @Test
    void getAllByMaster() {

        Master master = masterRepository.findById(1L).orElse(null);
        long id = master.getId();

        List<Registration> responseBody = webTestClient.get()
                .uri("registration/master/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Registration>>() {})
                .returnResult()
                .getResponseBody();

        for (Registration element : responseBody) {
            Assertions.assertEquals(element.getMaster(), master);
        }

    }

    /**
     * Тест поиска всех записей по клиенту
     */
    @Test
    void getAllByClient() {

        CarServiceClient client = clientRepository.findById(2L).orElse(null);
        long id = client.getId();

        List<Registration> responseBody = webTestClient.get()
                .uri("registration/client/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Registration>>() {})
                .returnResult()
                .getResponseBody();

        for (Registration element : responseBody) {
            Assertions.assertEquals(element.getCarServiceClient(), client);
        }
    }

    /**
     * Тест поиска всех записей по автомобилю
     */
    @Test
    void getAllByCar() {
        Car car = carRepository.findById(1L).orElse(null);
        long id = car.getId();

        List<Registration> responseBody = webTestClient.get()
                .uri("registration/car/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Registration>>() {})
                .returnResult()
                .getResponseBody();

        for (Registration element : responseBody) {
            Assertions.assertEquals(element.getCar(), car);
        }

    }

    /**
     * Тест получения будующих записей по мастеру
     */
    @Test
    void getActualByMaster() {
        Master master = masterRepository.findById(1L).orElse(null);

        registration2.setMaster(master);
        registrationRepository.save(registration2);

        List<Registration> responseBody = webTestClient.get()
                .uri("registration/master/actual/" + master.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Registration>>() {})
                .returnResult()
                .getResponseBody();

        for (Registration element : responseBody) {
            Assertions.assertTrue(element.getTimeFrom().isAfter(LocalDateTime.now()));
            Assertions.assertEquals(element.getMaster(), master);
        }

        Assertions.assertEquals(responseBody.size(), 1);

    }

    /**
     * Тест получения будующих записей по автомобилям
     */
    @Test
    void getActualByCar() {
        Car car = carRepository.findById(1L).orElse(null);

        registration2.setCar(car);
        registrationRepository.save(registration2);

        List<Registration> responseBody = webTestClient.get()
                .uri("registration/car/actual/" + car.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Registration>>() {})
                .returnResult()
                .getResponseBody();

        for (Registration element : responseBody) {
            Assertions.assertTrue(element.getTimeFrom().isAfter(LocalDateTime.now()));
            Assertions.assertEquals(element.getCar(), car);
        }

        Assertions.assertEquals(responseBody.size(), 1);
    }

    /**
     * Тест получения будующих записей по клиентам
     */
    @Test
    void getActualByClient() {
        CarServiceClient client = clientRepository.findById(1L).orElse(null);

        registration2.setCarServiceClient(client);
        registrationRepository.save(registration2);

        List<Registration> responseBody = webTestClient.get()
                .uri("registration/client/actual/" + client.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Registration>>() {})
                .returnResult()
                .getResponseBody();

        for (Registration element : responseBody) {
            Assertions.assertTrue(element.getTimeFrom().isAfter(LocalDateTime.now()));
            Assertions.assertEquals(element.getCarServiceClient(), client);
        }

        Assertions.assertEquals(responseBody.size(), 1);

    }
}