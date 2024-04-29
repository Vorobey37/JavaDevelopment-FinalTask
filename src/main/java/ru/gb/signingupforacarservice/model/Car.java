package ru.gb.signingupforacarservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name="cars")
@EqualsAndHashCode(callSuper = true)
public class Car extends ServiceModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="brand")
    private String  brand;

    @Column(name="model")
    private String model;

    @Column(name="engine_type")
    private String engineType;

    @Column(name="engine_volume")
    private float engineVolume;

    @Column(name="transmission_type")
    private String transmissionType;

    @Column(name="vin")
    private String VIN;

    @Column(name="state_number")
    private String stateNumber;
}
