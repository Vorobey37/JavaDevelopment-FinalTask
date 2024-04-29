package ru.gb.signingupforacarservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name="repair_types")
@EqualsAndHashCode(callSuper = true)
public class RepairType extends ServiceModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="duration_time")
    private long durationTime;

    @Column(name="price")
    private double price;
}
