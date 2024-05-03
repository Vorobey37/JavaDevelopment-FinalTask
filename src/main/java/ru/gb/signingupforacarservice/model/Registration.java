package ru.gb.signingupforacarservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Записи в автосервис
 */
@Entity
@Data
@Table(name="repair_registration")
@EqualsAndHashCode(callSuper = true)
public class Registration extends ServiceModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="client_id")
    private CarServiceClient carServiceClient;

    @ManyToOne
    @JoinColumn(name="car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name="repair_ig")
    private RepairType repairType;

    @ManyToOne
    @JoinColumn(name="master_id")
    private Master master;

    @Column(name="time_from")
    private LocalDateTime timeFrom;

    @Column(name="time_to")
    private LocalDateTime timeTo;
}
