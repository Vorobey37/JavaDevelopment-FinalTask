package ru.gb.signingupforacarservice.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import ru.gb.signingupforacarservice.model.Master;

import java.util.List;

class MasterControllerTest extends TestSpringBootBase{

    /**
     * Тестирование метода получения всех мастеров автосервиса
     */
    @Test
    void testGetAll(){
        List<Master> expectedMasters = masterRepository.findAll();

        List<Master> actualMasters = webTestClient.get()
                .uri("master/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Master>>() {})
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(expectedMasters.size(), actualMasters.size());

        for (int i = 0; i < expectedMasters.size(); i++) {
            Assertions.assertEquals(expectedMasters.get(i).getLastName(), actualMasters.get(i).getLastName());
            Assertions.assertEquals(expectedMasters.get(i).getFistName(), actualMasters.get(i).getFistName());
            Assertions.assertEquals(expectedMasters.get(i).getMiddleName(), actualMasters.get(i).getMiddleName());
            Assertions.assertEquals(expectedMasters.get(i).getSpecialization(), actualMasters.get(i).getSpecialization());
            Assertions.assertEquals(expectedMasters.get(i).getWorkExperience(), actualMasters.get(i).getWorkExperience());
        }
    }

    /**
     * Позитивное тестирование метода получения мастера по id
     */
    @Test
    void testGetByIdSuccess() {

        Master expectedMaster = new Master();
        expectedMaster.setLastName("Гагарин");
        expectedMaster.setFistName("Юрий");
        expectedMaster.setMiddleName("Алексеевич");
        expectedMaster.setSpecialization("Старший механик");
        expectedMaster.setWorkExperience(40.6f);

        masterRepository.save(expectedMaster);

        Master actualMaster = webTestClient.get()
                .uri("master/" + expectedMaster.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Master.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(actualMaster);
        Assertions.assertEquals(expectedMaster.getLastName(), actualMaster.getLastName());
        Assertions.assertEquals(expectedMaster.getFistName(), actualMaster.getFistName());
        Assertions.assertEquals(expectedMaster.getMiddleName(), actualMaster.getMiddleName());
        Assertions.assertEquals(expectedMaster.getSpecialization(), actualMaster.getSpecialization());
        Assertions.assertEquals(expectedMaster.getWorkExperience(), actualMaster.getWorkExperience());
    }

    /**
     * Негативное тестирование метода получения мастера по id
     */
    @Test
    void testGetByIdNotFound(){

        long notFoundId = 100;

        webTestClient.get()
                .uri("master/" + notFoundId)
                .exchange()
                .expectStatus().isNotFound();

    }

    /**
     * Позитивное тестирование метода создания мастера
     */
    @Test
    void testCreateSuccess(){

        Master expectedMaster = new Master();
        expectedMaster.setLastName("Гагарин");
        expectedMaster.setFistName("Юрий");
        expectedMaster.setMiddleName("Алексеевич");
        expectedMaster.setSpecialization("Старший механик");
        expectedMaster.setWorkExperience(40.6f);

        Master actualMaster = webTestClient.post()
                .uri("master")
                .bodyValue(expectedMaster)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Master.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(actualMaster);
        Assertions.assertEquals(expectedMaster.getLastName(), actualMaster.getLastName());
        Assertions.assertEquals(expectedMaster.getFistName(), actualMaster.getFistName());
        Assertions.assertEquals(expectedMaster.getMiddleName(), actualMaster.getMiddleName());
        Assertions.assertEquals(expectedMaster.getSpecialization(), actualMaster.getSpecialization());
        Assertions.assertEquals(expectedMaster.getWorkExperience(), actualMaster.getWorkExperience());

    }

    /**
     * Негативное тестирование с мастером, который уже есть в БД
     */
    @Test
    void testCreateConflictRequest(){

        webTestClient.post()
                .uri("master")
                .bodyValue(master1)
                .exchange()
                .expectStatus().isEqualTo(409);
    }

    /**
     * Позитивное тестирование редактирования мастера
     */
    @Test
    void testUpdateSuccess(){
        Master updatedMaster = new Master();
        updatedMaster.setLastName("Смирнов");
        updatedMaster.setFistName("Дмитрий");
        updatedMaster.setMiddleName("Александрович");
        updatedMaster.setSpecialization("Выездной механик");
        updatedMaster.setWorkExperience(2.6f);

        long testId = 1;

        Master actualMaster = webTestClient.put()
                .uri("master/" + testId)
                .bodyValue(updatedMaster)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Master.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(actualMaster);
        Assertions.assertEquals(updatedMaster.getLastName(), actualMaster.getLastName());
        Assertions.assertEquals(updatedMaster.getFistName(), actualMaster.getFistName());
        Assertions.assertEquals(updatedMaster.getMiddleName(), actualMaster.getMiddleName());
        Assertions.assertEquals(updatedMaster.getSpecialization(), actualMaster.getSpecialization());
        Assertions.assertEquals(updatedMaster.getWorkExperience(), actualMaster.getWorkExperience());
    }

    /**
     * Негативное тестирование редактирования мастера с несуществующим id
     */
    @Test
    void testUpdateNotFound(){
        Master updatedMaster = new Master();
        updatedMaster.setLastName("Гагарин");
        updatedMaster.setFistName("Юрий");
        updatedMaster.setMiddleName("Алексеевич");
        updatedMaster.setSpecialization("Старший механик");
        updatedMaster.setWorkExperience(40.6f);

        long testId = 100;

        webTestClient.put()
                .uri("master/" + testId)
                .bodyValue(updatedMaster)
                .exchange()
                .expectStatus().isNotFound();
    }

    /**
     * Негативное тестирование редактирования мастера с данными уже существующего мастера
     */
    @Test
    void testUpdateConflict(){
        long testId = 1;

        webTestClient.put()
                .uri("master/" + testId)
                .bodyValue(master2)
                .exchange()
                .expectStatus().isEqualTo(409);

    }

    /**
     * Позитивное тестирование удаления мастера
     */
    @Test
    void testDeleteSuccess(){
        long testId = master1.getId();
        long testSize = masterRepository.findAll().size();

        webTestClient.delete()
                .uri("master/" + testId)
                .exchange()
                .expectStatus().isOk();

        long actualSize = masterRepository.findAll().size();

        Assertions.assertEquals(testSize-1, actualSize);

        webTestClient.get()
                .uri("master/" + testId)
                .exchange()
                .expectStatus().isNotFound();

    }

    /**
     * Негативное тестирование метода с несуществующим id
     */
    @Test
    void testDeleteNotFound(){

        long testId = 100;

        webTestClient.delete()
                .uri("master/" + testId)
                .exchange()
                .expectStatus().isNotFound();
    }

}