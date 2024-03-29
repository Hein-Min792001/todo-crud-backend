package com.example.todocrudbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(nullable = false,unique = true)
    private String username;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @ManyToMany(fetch = FetchType.EAGER,
    cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private Set<Role> roles =
            new HashSet<>();
}
