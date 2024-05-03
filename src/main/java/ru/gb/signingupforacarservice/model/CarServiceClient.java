package ru.gb.signingupforacarservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Клиенты сервиса
 */
@Entity
@Data
@Table(name="car_service_clients")
@EqualsAndHashCode(callSuper = true)
public class CarServiceClient extends ServiceModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="last_name")
    private String lastName;

    @Column(name="first_name")
    private String fistName;

    @Column(name="middleName")
    private String middleName;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="email")
    private String email;

}
