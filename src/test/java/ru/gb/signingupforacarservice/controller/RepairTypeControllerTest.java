package ru.gb.signingupforacarservice.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import ru.gb.signingupforacarservice.model.RepairType;

import java.util.List;


class RepairTypeControllerTest extends TestSpringBootBase{

    /**
     * Тестирование метода получения всех видов ремонта
     */
    @Test
    void testGetAll(){
        List<RepairType> expectedRepairs = repairTypeRepository.findAll();

        List<RepairType> actualRepairs = webTestClient.get()
                .uri("repair/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<RepairType>>() {})
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(expectedRepairs.size(), actualRepairs.size());

        for (int i = 0; i < expectedRepairs.size(); i++) {
            Assertions.assertEquals(expectedRepairs.get(i).getName(), actualRepairs.get(i).getName());
            Assertions.assertEquals(expectedRepairs.get(i).getDurationTime(), actualRepairs.get(i).getDurationTime());
            Assertions.assertEquals(expectedRepairs.get(i).getPrice(), actualRepairs.get(i).getPrice());
        }
    }

    /**
     * Позитивное тестирование метода получения вида ремонта по id
     */
    @Test
    void testGetByIdSuccess() {

        RepairType expectedRepair = new RepairType();
        expectedRepair.setName("Регулировка клапанов");
        expectedRepair.setDurationTime(114);
        expectedRepair.setPrice(5320);

        repairTypeRepository.save(expectedRepair);

        RepairType actualRepair = webTestClient.get()
                .uri("repair/" + expectedRepair.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(RepairType.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(actualRepair);
        Assertions.assertEquals(expectedRepair.getName(), actualRepair.getName());
        Assertions.assertEquals(expectedRepair.getDurationTime(), actualRepair.getDurationTime());
        Assertions.assertEquals(expectedRepair.getPrice(), actualRepair.getPrice());
    }

    /**
     * Негативное тестирование метода получения вида ремонта по id
     */
    @Test
    void testGetByIdNotFound(){

        long notFoundId = 100;

        webTestClient.get()
                .uri("repair/" + notFoundId)
                .exchange()
                .expectStatus().isNotFound();

    }

    /**
     * Позитивное тестирование метода создания вида ремонта
     */
    @Test
    void testCreateSuccess(){

        RepairType expectedRepair = new RepairType();
        expectedRepair.setName("Регулировка клапанов");
        expectedRepair.setDurationTime(114);
        expectedRepair.setPrice(5320);

        RepairType actualRepair = webTestClient.post()
                .uri("repair")
                .bodyValue(expectedRepair)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(RepairType.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(actualRepair);
        Assertions.assertEquals(expectedRepair.getName(), actualRepair.getName());
        Assertions.assertEquals(expectedRepair.getDurationTime(), actualRepair.getDurationTime());
        Assertions.assertEquals(expectedRepair.getPrice(), actualRepair.getPrice());
    }

    /**
     * Негативное тестирование с видом ремонта, который уже есть в БД
     */
    @Test
    void testCreateConflictRequest(){

        webTestClient.post()
                .uri("repair")
                .bodyValue(repairType1)
                .exchange()
                .expectStatus().isEqualTo(409);
    }

    /**
     * Позитивное тестирование редактирования вида ремонта
     */
    @Test
    void testUpdateSuccess(){
        RepairType updatedRepair = new RepairType();
        updatedRepair.setName("Замена шкворня");
        updatedRepair.setDurationTime(294);
        updatedRepair.setPrice(18650);

        long testId = 1;

        RepairType actualRepair = webTestClient.put()
                .uri("repair/" + testId)
                .bodyValue(updatedRepair)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RepairType.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(actualRepair);
        Assertions.assertEquals(updatedRepair.getName(), actualRepair.getName());
        Assertions.assertEquals(updatedRepair.getDurationTime(), actualRepair.getDurationTime());
        Assertions.assertEquals(updatedRepair.getPrice(), actualRepair.getPrice());
    }

    /**
     * Негативное тестирование редактирования вида ремонта с несуществующим id
     */
    @Test
    void testUpdateNotFound(){
        RepairType updatedRepair = new RepairType();
        updatedRepair.setName("Регулировка клапанов");
        updatedRepair.setDurationTime(114);
        updatedRepair.setPrice(5320);

        long testId = 100;

        webTestClient.put()
                .uri("repair/" + testId)
                .bodyValue(updatedRepair)
                .exchange()
                .expectStatus().isNotFound();
    }

    /**
     * Негативное тестирование редактирования вида ремонта с данными уже существующего ремонта
     */
    @Test
    void testUpdateConflict(){
        long testId = 1;

        webTestClient.put()
                .uri("repair/" + testId)
                .bodyValue(repairType2)
                .exchange()
                .expectStatus().isEqualTo(409);

    }

    /**
     * Позитивное тестирование удаления вида ремонта
     */
    @Test
    void testDeleteSuccess(){
        long testId = repairType1.getId();
        long testSize = repairTypeRepository.findAll().size();

        webTestClient.delete()
                .uri("repair/" + testId)
                .exchange()
                .expectStatus().isOk();

        long actualSize = repairTypeRepository.findAll().size();

        Assertions.assertEquals(testSize-1, actualSize);

        webTestClient.get()
                .uri("repair/" + testId)
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
                .uri("repair/" + testId)
                .exchange()
                .expectStatus().isNotFound();
    }

}