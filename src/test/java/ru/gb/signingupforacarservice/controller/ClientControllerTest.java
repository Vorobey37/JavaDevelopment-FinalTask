package ru.gb.signingupforacarservice.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import ru.gb.signingupforacarservice.model.CarServiceClient;

import java.util.List;


class ClientControllerTest extends TestSpringBootBase{

    /**
     * Тестирование метода получения всех клиентов автосервиса
     */
    @Test
    void testGetAll(){
        List<CarServiceClient> expectedClients = clientRepository.findAll();

        List<CarServiceClient> actualClients = webTestClient.get()
                .uri("client/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<CarServiceClient>>() {})
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(expectedClients.size(), actualClients.size());

        for (int i = 0; i < expectedClients.size(); i++) {
            Assertions.assertEquals(expectedClients.get(i).getLastName(), actualClients.get(i).getLastName());
            Assertions.assertEquals(expectedClients.get(i).getFistName(), actualClients.get(i).getFistName());
            Assertions.assertEquals(expectedClients.get(i).getMiddleName(), actualClients.get(i).getMiddleName());
            Assertions.assertEquals(expectedClients.get(i).getPhoneNumber(), actualClients.get(i).getPhoneNumber());
            Assertions.assertEquals(expectedClients.get(i).getEmail(), actualClients.get(i).getEmail());
        }
    }

    /**
     * Позитивное тестирование метода получения клиентов по id
     */
    @Test
    void testGetByIdSuccess() {

        CarServiceClient expectedClient = new CarServiceClient();
        expectedClient.setLastName("Пупкин");
        expectedClient.setFistName("Василий");
        expectedClient.setMiddleName("Иванович");
        expectedClient.setPhoneNumber("8911-911-91-91");
        expectedClient.setEmail("pupkin@mail.ru");

        clientRepository.save(expectedClient);

        CarServiceClient actualClient = webTestClient.get()
                .uri("client/" + expectedClient.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(CarServiceClient.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(actualClient);
        Assertions.assertEquals(expectedClient.getLastName(), actualClient.getLastName());
        Assertions.assertEquals(expectedClient.getFistName(), actualClient.getFistName());
        Assertions.assertEquals(expectedClient.getMiddleName(), actualClient.getMiddleName());
        Assertions.assertEquals(expectedClient.getPhoneNumber(), actualClient.getPhoneNumber());
        Assertions.assertEquals(expectedClient.getEmail(), actualClient.getEmail());
    }

    /**
     * Негативное тестирование метода получения клиента по id
     */
    @Test
    void testGetByIdNotFound(){

        long notFoundId = 100;

        webTestClient.get()
                .uri("client/" + notFoundId)
                .exchange()
                .expectStatus().isNotFound();

    }

    /**
     * Позитивное тестирование метода создания клиента автосервиса
     */
    @Test
    void testCreateSuccess(){

        CarServiceClient expectedClient = new CarServiceClient();
        expectedClient.setLastName("Пупкин");
        expectedClient.setFistName("Василий");
        expectedClient.setMiddleName("Иванович");
        expectedClient.setPhoneNumber("8911-911-91-91");
        expectedClient.setEmail("pupkin@mail.ru");

        CarServiceClient actualClient = webTestClient.post()
                .uri("client")
                .bodyValue(expectedClient)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CarServiceClient.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(actualClient);
        Assertions.assertEquals(expectedClient.getLastName(), actualClient.getLastName());
        Assertions.assertEquals(expectedClient.getFistName(), actualClient.getFistName());
        Assertions.assertEquals(expectedClient.getMiddleName(), actualClient.getMiddleName());
        Assertions.assertEquals(expectedClient.getPhoneNumber(), actualClient.getPhoneNumber());
        Assertions.assertEquals(expectedClient.getEmail(), actualClient.getEmail());

    }

    /**
     * Позитивное тестирование редактирования клиента на сервис
     */
    @Test
    void testUpdateSuccess(){
        CarServiceClient updatedClient = new CarServiceClient();
        updatedClient.setLastName("Пупкин");
        updatedClient.setFistName("Василий");
        updatedClient.setMiddleName("Иванович");
        updatedClient.setPhoneNumber("8911-911-91-91");
        updatedClient.setEmail("pupkin@mail.ru");

        long testId = 1;

        CarServiceClient actualClient = webTestClient.put()
                .uri("client/" + testId)
                .bodyValue(updatedClient)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CarServiceClient.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(actualClient);
        Assertions.assertEquals(updatedClient.getLastName(), actualClient.getLastName());
        Assertions.assertEquals(updatedClient.getFistName(), actualClient.getFistName());
        Assertions.assertEquals(updatedClient.getMiddleName(), actualClient.getMiddleName());
        Assertions.assertEquals(updatedClient.getPhoneNumber(), actualClient.getPhoneNumber());
        Assertions.assertEquals(updatedClient.getEmail(), actualClient.getEmail());
    }

    /**
     * Негативное тестирование редактирования клиента с несуществующим id
     */
    @Test
    void testUpdateNotFound(){
        CarServiceClient updatedClient = new CarServiceClient();
        updatedClient.setLastName("Пупкин");
        updatedClient.setFistName("Василий");
        updatedClient.setMiddleName("Иванович");
        updatedClient.setPhoneNumber("8911-911-91-91");
        updatedClient.setEmail("pupkin@mail.ru");

        long testId = 100;

        webTestClient.put()
                .uri("client/" + testId)
                .bodyValue(updatedClient)
                .exchange()
                .expectStatus().isNotFound();
    }


    /**
     * Позитивное тестирование удаления клиента
     */
    @Test
    void testDeleteSuccess(){
        long testId = client1.getId();
        long testSize = clientRepository.findAll().size();

        webTestClient.delete()
                .uri("client/" + testId)
                .exchange()
                .expectStatus().isOk();

        long actualSize = clientRepository.findAll().size();

        Assertions.assertEquals(testSize-1, actualSize);

        webTestClient.get()
                .uri("client/" + testId)
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
                .uri("client/" + testId)
                .exchange()
                .expectStatus().isNotFound();
    }


}