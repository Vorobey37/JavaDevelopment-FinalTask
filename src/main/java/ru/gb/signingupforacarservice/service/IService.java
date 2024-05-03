package ru.gb.signingupforacarservice.service;

import java.util.List;

/**
 * Интерфейс для последующей реализации методов в имплементирующих его классах
 * @param <T> Класс передаваемого объекта
 */
public interface IService<T> {

    /**
     * Поиск всех объектов
     * @return Список найденных объектов
     */
    List<T> findAll();

    /**
     * Поиск объекта по id
     * @param id id искомого объекта
     * @return найденный объект
     */
    T findById(long id);

    /**
     * Создание объекта
     * @param element Передаваемый объект для создания
     * @return Созданный объект
     */
    T create(T element);

    /**
     * Редактирование объекта
     * @param id id объекта, который нужно отредактировать
     * @param element Отредактированный объект
     * @return Отредактированный объект
     */
    T update(long id, T element);

    /**
     * Удаление объекта по id
     * @param id id удаляемого объекта
     */
    void delete(long id);
}
