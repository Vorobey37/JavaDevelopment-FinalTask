package ru.gb.signingupforacarservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.signingupforacarservice.model.ServiceModel;
import java.util.List;

/** Интерфейс для последующей реализации методов в имплементирующих его классах
 * @param <T>
 */
public interface IController<T extends ServiceModel> {

    /**
     * Получение всех объектов класса-наследника ServiceModel в теле http-ответа
     * @return список объектов класса-наследника ServiceModel в теле http-ответа
     */
    @GetMapping("all")
    ResponseEntity<List<T>> getAll();

    /**
     * Получение объекта класса-наследника ServiceModel по его id в теле http-ответа
     * @param id id объекта класса-наследника ServiceModel
     * @return объект класса-наследника ServiceModel по его id в теле http-ответа
     */
    @GetMapping("{id}")
    ResponseEntity<T> getById(@PathVariable long id);

    /**
     * Создание объекта класса-наследника ServiceModel
     * @param requestObject передаваемый объект класса-наследника ServiceModel в виде параметра RequestBody
     * @return объект класса-наследника ServiceModel в теле http-ответа
     */
    @PostMapping()
    ResponseEntity<T> create(@RequestBody T requestObject);

    /**
     * Редактирование объекта
     * @param id id объекта класса-наследника ServiceModel, который необходимо отредактировать
     * @param requestObject передаваемый, отредактированный объект класса-наследника ServiceModel в виде параметра RequestBody
     * @return объект класса-наследника ServiceModel в теле http-ответа
     */
    @PutMapping("{id}")
    ResponseEntity<T> updateById(@PathVariable long id, @RequestBody T requestObject);

    /**
     * Удаление объекта класса-наследника ServiceModel
     * @param id объекта класса-наследника ServiceModel для удаления
     * @return http-ответ
     */
    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteById(@PathVariable long id);
}
