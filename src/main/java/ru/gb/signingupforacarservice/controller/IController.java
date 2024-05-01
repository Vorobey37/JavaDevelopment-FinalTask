package ru.gb.signingupforacarservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.signingupforacarservice.model.ServiceModel;
import java.util.List;

public interface IController<T extends ServiceModel> {

    @GetMapping("all")
    ResponseEntity<List<T>> getAll();

    @GetMapping("{id}")
    ResponseEntity<T> getById(@PathVariable long id);

    @PostMapping()
    ResponseEntity<T> create(@RequestBody T requestObject);

    @PutMapping("{id}")
    ResponseEntity<T> updateById(@PathVariable long id, @RequestBody T requestObject);

    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteById(@PathVariable long id);
}
