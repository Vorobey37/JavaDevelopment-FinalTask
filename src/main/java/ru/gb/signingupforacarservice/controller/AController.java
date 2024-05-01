package ru.gb.signingupforacarservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.gb.signingupforacarservice.model.ServiceModel;
import ru.gb.signingupforacarservice.service.AService;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
public abstract class AController<T extends AService<E, ?>, E extends ServiceModel> implements IController<E> {

    protected final T service;

    @Override
    public ResponseEntity<List<E>> getAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @Override
    public ResponseEntity<E> getById(long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

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

    @Override
    public ResponseEntity<E> updateById(long id, E requestObject) {
        return ResponseEntity.ok().body(service.update(id, requestObject));
    }

    @Override
    public ResponseEntity<Void> deleteById(long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
