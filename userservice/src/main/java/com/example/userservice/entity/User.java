package com.example.userservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 10, max = 14)
    private String phoneNo;

    @NotNull
    private LocalDate dob;

    @NotBlank
    @Size(min = 10, max = 50)
    private String username;

    @NotBlank
    private String password;

    @NotNull
    private boolean isSoftDeleted;

    @NotNull
    private boolean isBanned;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(
            String name, String email, String phoneNo, LocalDate dob, String username,
            String password, boolean isSoftDeleted, boolean isBanned, Role role
    ){
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.dob = dob;
        this.username = username;
        this.password = password;
        this.isSoftDeleted = isSoftDeleted;
        this.isBanned = isBanned;
        this.role = role;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
