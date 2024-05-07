package ru.gb.signingupforacarservice.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import ru.gb.signingupforacarservice.model.Car;
import ru.gb.signingupforacarservice.model.Registration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
}