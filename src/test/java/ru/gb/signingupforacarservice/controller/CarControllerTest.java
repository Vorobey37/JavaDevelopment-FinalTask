package ru.gb.signingupforacarservice.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import ru.gb.signingupforacarservice.model.Car;
import java.util.List;

class CarControllerTest extends TestSpringBootBase{

    /**
     * Тестирование метода получения всех автомобилей
     */
    @Test
    void testGetAll(){
        List<Car> expectedCars = carRepository.findAll();

        List<Car> actualCars = webTestClient.get()
                .uri("car/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Car>>() {})
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(expectedCars.size(), actualCars.size());

        for (int i = 0; i < expectedCars.size(); i++) {
            Assertions.assertEquals(expectedCars.get(i).getBrand(), actualCars.get(i).getBrand());
            Assertions.assertEquals(expectedCars.get(i).getModel(), actualCars.get(i).getModel());
            Assertions.assertEquals(expectedCars.get(i).getEngineType(), actualCars.get(i).getEngineType());
            Assertions.assertEquals(expectedCars.get(i).getEngineVolume(), actualCars.get(i).getEngineVolume());
            Assertions.assertEquals(expectedCars.get(i).getTransmissionType(), actualCars.get(i).getTransmissionType());
            Assertions.assertEquals(expectedCars.get(i).getVIN(), actualCars.get(i).getVIN());
            Assertions.assertEquals(expectedCars.get(i).getStateNumber(), actualCars.get(i).getStateNumber());
        }
    }

    /**
     * Позитивное тестирование метода получения автомобилей по id
     */
    @Test
    void testGetByIdSuccess() {

        Car expectedCar = new Car();
        expectedCar.setBrand("BMW");
        expectedCar.setModel("525");
        expectedCar.setEngineType("Бензин");
        expectedCar.setEngineVolume(2.0f);
        expectedCar.setTransmissionType("МКПП");
        expectedCar.setVIN("WEXNCLVN8749563");
        expectedCar.setStateNumber("Т555ТК35");

        carRepository.save(expectedCar);

        Car actualCar = webTestClient.get()
                .uri("car/" + expectedCar.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Car.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(actualCar);
        Assertions.assertEquals(expectedCar.getBrand(), actualCar.getBrand());
        Assertions.assertEquals(expectedCar.getModel(), actualCar.getModel());
        Assertions.assertEquals(expectedCar.getEngineType(), actualCar.getEngineType());
        Assertions.assertEquals(expectedCar.getEngineVolume(), actualCar.getEngineVolume());
        Assertions.assertEquals(expectedCar.getTransmissionType(), actualCar.getTransmissionType());
        Assertions.assertEquals(expectedCar.getVIN(), actualCar.getVIN());
        Assertions.assertEquals(expectedCar.getStateNumber(), actualCar.getStateNumber());
    }

    /**
     * Негативное тестирование метода получения автомобиля по id
     */
    @Test
    void testGetByIdNotFound(){

        long notFoundId = 100;

        webTestClient.get()
                .uri("car/" + notFoundId)
                .exchange()
                .expectStatus().isNotFound();

    }

    /**
     * Позитивное тестирование метода создания автомобиля
     */
    @Test
    void testCreateSuccess(){

        Car expectedCar = new Car();
        expectedCar.setBrand("BMW");
        expectedCar.setModel("525");
        expectedCar.setEngineType("Бензин");
        expectedCar.setEngineVolume(2.0f);
        expectedCar.setTransmissionType("МКПП");
        expectedCar.setVIN("WEXNCLVN8749563");
        expectedCar.setStateNumber("Т555ТК35");

        Car actualCar = webTestClient.post()
                .uri("car")
                .bodyValue(expectedCar)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Car.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(actualCar);
        Assertions.assertEquals(expectedCar.getBrand(), actualCar.getBrand());
        Assertions.assertEquals(expectedCar.getModel(), actualCar.getModel());
        Assertions.assertEquals(expectedCar.getEngineType(), actualCar.getEngineType());
        Assertions.assertEquals(expectedCar.getEngineVolume(), actualCar.getEngineVolume());
        Assertions.assertEquals(expectedCar.getTransmissionType(), actualCar.getTransmissionType());
        Assertions.assertEquals(expectedCar.getVIN(), actualCar.getVIN());
        Assertions.assertEquals(expectedCar.getStateNumber(), actualCar.getStateNumber());

    }

    /**
     * Негативное тестирование с автомобилем, который уже есть в БД
     */
    @Test
    void testCreateConflictRequest(){

        webTestClient.post()
                .uri("car")
                .bodyValue(car1)
                .exchange()
                .expectStatus().isEqualTo(409);

    }

    /**
     * Позитивное тестирование редактирования записи на сервис
     */
    @Test
    void testUpdateSuccess(){
        Car updatedCar = new Car();
        updatedCar.setBrand("BMW");
        updatedCar.setModel("525");
        updatedCar.setEngineType("Бензин");
        updatedCar.setEngineVolume(2.0f);
        updatedCar.setTransmissionType("МКПП");
        updatedCar.setVIN("RTKUYTFD025698");
        updatedCar.setStateNumber("Т555ТК35");

        long testId = 1;

        Car actualCar = webTestClient.put()
                .uri("car/" + testId)
                .bodyValue(updatedCar)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Car.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(actualCar);
        Assertions.assertEquals(updatedCar.getBrand(), actualCar.getBrand());
        Assertions.assertEquals(updatedCar.getModel(), actualCar.getModel());
        Assertions.assertEquals(updatedCar.getEngineType(), actualCar.getEngineType());
        Assertions.assertEquals(updatedCar.getEngineVolume(), actualCar.getEngineVolume());
        Assertions.assertEquals(updatedCar.getTransmissionType(), actualCar.getTransmissionType());
        Assertions.assertEquals(updatedCar.getVIN(), actualCar.getVIN());
        Assertions.assertEquals(updatedCar.getStateNumber(), actualCar.getStateNumber());
    }

    /**
     * Негативное тестирование редактирования автомобиля с несуществующим id
     */
    @Test
    void testUpdateNotFound(){
        Car updatedCar = new Car();
        updatedCar.setBrand("BMW");
        updatedCar.setModel("525");
        updatedCar.setEngineType("Бензин");
        updatedCar.setEngineVolume(2.0f);
        updatedCar.setTransmissionType("МКПП");
        updatedCar.setVIN("WEXNCLVN8749563");
        updatedCar.setStateNumber("Т555ТК35");

        long testId = 100;

        webTestClient.put()
                .uri("car/" + testId)
                .bodyValue(updatedCar)
                .exchange()
                .expectStatus().isNotFound();
    }

    /**
     * Негативное тестирование редактирования автомобиля с данными уже существующего автомобиля
     */
    @Test
    void testUpdateConflict(){
        long testId = 1;

        webTestClient.put()
                .uri("car/" + testId)
                .bodyValue(car2)
                .exchange()
                .expectStatus().isEqualTo(409);

    }

    /**
     * Позитивное тестирование удаления автомобиля
     */
    @Test
    void testDeleteSuccess(){
        long testId = car1.getId();
        long testSize = carRepository.findAll().size();

        webTestClient.delete()
                .uri("car/" + testId)
                .exchange()
                .expectStatus().isOk();

        long actualSize = carRepository.findAll().size();

        Assertions.assertEquals(testSize-1, actualSize);

        webTestClient.get()
                .uri("car/" + testId)
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
                .uri("car/" + testId)
                .exchange()
                .expectStatus().isNotFound();
    }


}