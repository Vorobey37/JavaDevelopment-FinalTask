package ru.gb.signingupforacarservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ru.gb.signingupforacarservice.model.ServiceModel;

import java.util.List;


@RequiredArgsConstructor
public abstract class AService<T extends ServiceModel, E extends JpaRepository<T, Long>> implements IService<T> {

    protected static final String NOT_FOUND_MESSAGE = "Объект не найден с id: ";
    protected static final String BAD_REQUEST_MESSAGE = "Передаваемый объект не может быть null";
    protected static final String CONFLICT_MESSAGE = "Такой объект уже существует";

    protected final E repository;

    @Override
    public T findById(long id){
        T object = repository.findById(id).orElse(null);

        if (object == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE + id);
        }

        return object;
    }

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

    @Override
    public List<T> findAll(){

        return repository.findAll();
    }

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

    @Override
    public void delete(long id){
        checkExistsIdEntityInDB(id);

        repository.deleteById(id);
    }


    protected void checkRequest(T requestObject){
        if (requestObject == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE);
        }
    }

    protected void checkExistsIdEntityInDB(long id){
        if(!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE + id);
        }
    }

    protected boolean checkExistsEntityInDB(T requestObject){
        return false;
    }

    protected List<T> findByCustomFields(T requestObject){
        return null;
    }
}
