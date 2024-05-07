package ru.gb.signingupforacarservice.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import ru.gb.signingupforacarservice.model.AuthorizedUser;
import java.util.List;

class UserControllerTest extends TestSpringBootBase{

    /**
     * Тестирование метода получения всех пользователей
     */
    @Test
    void testGetAll(){
        List<AuthorizedUser> expectedUsers = userRepository.findAll();

        List<AuthorizedUser> actualUsers = webTestClient.get()
                .uri("user/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<AuthorizedUser>>() {})
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(expectedUsers.size(), actualUsers.size());

        for (int i = 0; i < expectedUsers.size(); i++) {
            Assertions.assertEquals(expectedUsers.get(i).getLogin(), actualUsers.get(i).getLogin());
            Assertions.assertEquals(expectedUsers.get(i).getPassword(), actualUsers.get(i).getPassword());
            Assertions.assertEquals(expectedUsers.get(i).getRole(), actualUsers.get(i).getRole());
        }
    }

    /**
     * Позитивное тестирование метода получения пользователя по id
     */
    @Test
    void testGetByIdSuccess() {

        AuthorizedUser expectedUser = new AuthorizedUser();
        expectedUser.setLogin("userLogin");
        expectedUser.setPassword("userPassword");
        expectedUser.setRole("CLIENT");

        userRepository.save(expectedUser);

        AuthorizedUser actualUser = webTestClient.get()
                .uri("user/" + expectedUser.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthorizedUser.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser.getLogin(), actualUser.getLogin());
        Assertions.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        Assertions.assertEquals(expectedUser.getRole(), actualUser.getRole());
    }

    /**
     * Негативное тестирование метода получения пользователя по id
     */
    @Test
    void testGetByIdNotFound(){

        long notFoundId = 100;

        webTestClient.get()
                .uri("user/" + notFoundId)
                .exchange()
                .expectStatus().isNotFound();

    }

    /**
     * Позитивное тестирование метода создания пользователя
     */
    @Test
    void testCreateSuccess(){

        AuthorizedUser expectedUser = new AuthorizedUser();
        expectedUser.setLogin("userLogin");
        expectedUser.setPassword("userPassword");
        expectedUser.setRole("CLIENT");

        AuthorizedUser actualUser = webTestClient.post()
                .uri("user")
                .bodyValue(expectedUser)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AuthorizedUser.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser.getLogin(), actualUser.getLogin());
        Assertions.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        Assertions.assertEquals(expectedUser.getRole(), actualUser.getRole());
    }

    /**
     * Негативное тестирование с пользователем, который уже есть в БД
     */
    @Test
    void testCreateConflictRequest(){

        webTestClient.post()
                .uri("user")
                .bodyValue(user1)
                .exchange()
                .expectStatus().isEqualTo(409);
    }

    /**
     * Позитивное тестирование редактирования пользователя
     */
    @Test
    void testUpdateSuccess(){
        AuthorizedUser updatedUser = new AuthorizedUser();
        updatedUser.setLogin("updateLogin");
        updatedUser.setPassword("updatePassword");
        updatedUser.setRole("CLIENT");

        long testId = 1;

        AuthorizedUser actualUser = webTestClient.put()
                .uri("user/" + testId)
                .bodyValue(updatedUser)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthorizedUser.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(updatedUser.getLogin(), actualUser.getLogin());
        Assertions.assertEquals(updatedUser.getPassword(), actualUser.getPassword());
        Assertions.assertEquals(updatedUser.getRole(), actualUser.getRole());
    }

    /**
     * Негативное тестирование редактирования пользователя с несуществующим id
     */
    @Test
    void testUpdateNotFound(){
        AuthorizedUser updatedUser = new AuthorizedUser();
        updatedUser.setLogin("updateLogin");
        updatedUser.setPassword("updatePassword");
        updatedUser.setRole("CLIENT");

        long testId = 100;

        webTestClient.put()
                .uri("user/" + testId)
                .bodyValue(updatedUser)
                .exchange()
                .expectStatus().isNotFound();
    }

    /**
     * Негативное тестирование редактирования пользователя с данными уже существующего пользователя
     */
    @Test
    void testUpdateConflict(){
        long testId = 1;

        webTestClient.put()
                .uri("user/" + testId)
                .bodyValue(user2)
                .exchange()
                .expectStatus().isEqualTo(409);

    }

    /**
     * Позитивное тестирование удаления пользователя
     */
    @Test
    void testDeleteSuccess(){
        long testId = user1.getId();
        long testSize = userRepository.findAll().size();

        webTestClient.delete()
                .uri("user/" + testId)
                .exchange()
                .expectStatus().isOk();

        long actualSize = userRepository.findAll().size();

        Assertions.assertEquals(testSize-1, actualSize);

        webTestClient.get()
                .uri("user/" + testId)
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
                .uri("user/" + testId)
                .exchange()
                .expectStatus().isNotFound();
    }


}