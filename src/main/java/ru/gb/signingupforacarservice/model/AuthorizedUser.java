package ru.gb.signingupforacarservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Авторизованные пользователи
 */
@Entity
@Data
@Table(name="authorized_users")
@EqualsAndHashCode(callSuper = true)
public class AuthorizedUser extends ServiceModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="login")
    private String login;

    @Column(name="password")
    private String password;

    @Column(name="role")
    private String role;
}
