package ru.gb.signingupforacarservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Мастера, выполняющие ремонты
 */
@Data
@Entity
@Table(name="masters")
@EqualsAndHashCode(callSuper = false)
public class Master extends ServiceModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="last_name")
    private String lastName;

    @Column(name="first_name")
    private String fistName;

    @Column(name="middleName")
    private String middleName;

    @Column(name="specialization")
    private String specialization;

    @Column(name="work_experience")
    private float workExperience;

}
