package ru.gb.signingupforacarservice.model;

import lombok.Data;

/**
 * Абстрактный класс, родитель последующих сущностей автосервиса
 */
@Data
public abstract class ServiceModel {

    protected Long id;
}
