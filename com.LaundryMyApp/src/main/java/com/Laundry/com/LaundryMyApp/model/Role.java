package com.Laundry.com.LaundryMyApp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private List<UserLaundry> usersLaundry;

    public Role() {
    }

    public Role(String role) {
        super();
        this.role = role;
    }
}

