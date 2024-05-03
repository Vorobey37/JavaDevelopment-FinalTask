package ru.gb.signingupforacarservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.gb.signingupforacarservice.model.ServiceModel;
import ru.gb.signingupforacarservice.service.AService;

import java.net.URI;
import java.util.List;

/**
 * Абстрактный класс, родитель последующих контроллеров
 * @param <T> обобщенный класс, наследник родительского класса AService
 * @param <E> обобщенный класс, наследник родительского класса ServiceModel
 */
@RequiredArgsConstructor
public abstract class AController<T extends AService<E, ?>, E extends ServiceModel> implements IController<E> {

    protected final T service;


    /**
     * Получение всех объектов класса-наследника ServiceModel в теле http-ответа
     * @return список объектов класса-наследника ServiceModel в теле http-ответа
     */
    @Override
    public ResponseEntity<List<E>> getAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    /**
     * Получение объекта класса-наследника ServiceModel по его id в теле http-ответа
     * @param id id объекта класса-наследника ServiceModel
     * @return объект класса-наследника ServiceModel по его id в теле http-ответа
     */
    @Override
    public ResponseEntity<E> getById(long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    /**
     * Создание объекта класса-наследника ServiceModel
     * @param requestObject передаваемый объект класса-наследника ServiceModel
     * @return объект класса-наследника ServiceModel в теле http-ответа
     */
    @Override
    public ResponseEntity<E> create(E requestObject) {
        E newObject = service.create(requestObject);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newObject.getId())
                .toUri();
        return ResponseEntity.created(location).body(newObject);
    }

    /**
     * Редактирование объекта класса-наследника ServiceModel
     * @param id id объекта класса-наследника ServiceModel, который необходимо отредактировать
     * @param requestObject передаваемый, отредактированный объект класса-наследника ServiceModel
     * @return объект класса-наследника ServiceModel в теле http-ответа
     */
    @Override
    public ResponseEntity<E> updateById(long id, E requestObject) {
        return ResponseEntity.ok().body(service.update(id, requestObject));
    }

    /**
     * Удаление объекта класса-наследника ServiceModel
     * @param id объекта класса-наследника ServiceModel для удаления
     * @return http-ответ
     */
    @Override
    public ResponseEntity<Void> deleteById(long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
