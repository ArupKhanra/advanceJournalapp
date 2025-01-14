package com.arupkhanra.advanceSpringbootFeaturesAZ.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "userName"))
public class User {

    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName;

    @NotBlank(message = "Email cannot be blank")
    @Email
    private String email;

    private String sentimentAnalisis;

    @Column(nullable = false)
    private String password;

    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JournalEntry> journalEntries = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<String> roles = new ArrayList<>();

    // Additional constructors, methods, or logic can be added here if needed
}