package com.arupkhanra.advanceSpringbootFeaturesAZ.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", indexes = {
        @Index(name = "idx_username", columnList = "username", unique = true)
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(unique = true, nullable = false)
    private String userName;

    @NotNull  // Ensure password is not null at the database level
    private String password;

    // Mapped by the 'user' field in JournalEntry
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<JournalEntry> journalEntries = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)  // Eager loading to avoid lazy initialization issues
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<String> roles = new ArrayList<>();  // Consider using ImmutableList for immutability
}
