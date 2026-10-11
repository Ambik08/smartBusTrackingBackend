package com.sbapp.smartBusTrackinggg.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String organisationName;

    private String email;

    private String phoneNumber;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    private Boolean accepted = false;

    private String t;

    private Boolean active = false;

    @OneToMany( mappedBy = "user" ,cascade=CascadeType.ALL , orphanRemoval = true)
    @JsonManagedReference
    private List<Route> routes = new ArrayList<>();

    private String role="organisationOwner";
}