package ru.gb.signingupforacarservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ru.gb.signingupforacarservice.model.ServiceModel;

import java.util.List;


/**
 * Абстрактный класс, родитель последующих сервисов
 * @param <T> обобщенный класс, наследник родительского класса ServiceModel
 * @param <E> обобщенный класс, наследник родительского класса JpaRepository
 */
@RequiredArgsConstructor
public abstract class AService<T extends ServiceModel, E extends JpaRepository<T, Long>> implements IService<T> {

    protected static final String NOT_FOUND_MESSAGE = "Объект не найден с id: ";
    protected static final String BAD_REQUEST_MESSAGE = "Передаваемый объект не может быть null";
    protected static final String CONFLICT_MESSAGE = "Такой объект уже существует";

    protected final E repository;

    /**
     * Поиск объекта-наследника класса ServiceModel по id
     * @param id id для поиска
     * @return объект-наследник класса ServiceModel
     */
    @Override
    public T findById(long id){
        T object = repository.findById(id).orElse(null);

        if (object == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE + id);
        }

        return object;
    }

    /**
     * Создание объекта-наследника класса ServiceModel
     * @param requestObject передаваемый объект-наследник класса ServiceModel
     * @return созданный объект
     */
    @Override
    public T create(T requestObject){
        checkRequest(requestObject);

        if (checkExistsEntityInDB(requestObject)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, CONFLICT_MESSAGE);
        }

        if(requestObject.getId() != null){
            requestObject.setId(null);
        }

        return repository.save(requestObject);
    }

    /**
     * Поиск всех объектов конкретного типа-наследника класса ServiceModel
     * @return список объектов-наследников класса ServiceModel
     */
    @Override
    public List<T> findAll(){

        return repository.findAll();
    }

    /**
     * Редактирование объектов-наследников класса ServiceModel по id
     * @param id id объекта, который нужно отредактировать
     * @param requestObject отредактированный объект
     * @return отредактированный объект-наследник класса ServiceModel
     */
    @Override
    public T update(long id, T requestObject){
        checkExistsIdEntityInDB(id);
        checkRequest(requestObject);

        if(checkExistsEntityInDB(requestObject)){
            List<T> objects = findByCustomFields(requestObject);

            int count = 0;
            for (T element : objects) {
                if (element.getId() == id){
                    count++;
                }
            }
            if (count == 0){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Объект не уникален!");
            }
        }
        requestObject.setId(id);

        return repository.save(requestObject);
    }

    /**
     * Удаление объекта по id
     * @param id id объекта для удаления
     */
    @Override
    public void delete(long id){
        checkExistsIdEntityInDB(id);

        repository.deleteById(id);
    }


    /**
     * Проверка передаваемого объекта
     * @param requestObject передаваемый объект-наследник класса ServiceModel
     */
    protected void checkRequest(T requestObject){
        if (requestObject == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE);
        }
    }

    /**
     * Проверка существования объекта в БД по id
     * @param id id объекта для поиска
     */
    protected void checkExistsIdEntityInDB(long id){
        if(!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE + id);
        }
    }

    /**
     * Проверка существования объекта в БД
     * @param requestObject Передаваемый объект-наследник класса ServiceModel для проверки
     * @return логический результат проверки
     */
    protected boolean checkExistsEntityInDB(T requestObject){
        return false;
    }

    /**
     * Поиск объектов в БД по заданным полям
     * @param requestObject Передаваемый объект-наследник класса ServiceModel для поиска
     * @return Список найденных объектов-наследников класса ServiceModel
     */
    protected List<T> findByCustomFields(T requestObject){
        return null;
    }
}
