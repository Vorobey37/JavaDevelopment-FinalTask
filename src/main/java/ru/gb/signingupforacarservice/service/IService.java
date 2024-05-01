package ru.gb.signingupforacarservice.service;

import java.util.List;

public interface IService<T> {

    List<T> findAll();
    T findById(long id);
    T create(T element);
    T update(long id, T element);
    void delete(long id);
}
