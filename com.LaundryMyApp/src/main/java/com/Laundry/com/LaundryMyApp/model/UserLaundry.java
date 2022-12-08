package com.Laundry.com.LaundryMyApp.model;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserLaundry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    @NotEmpty(message = "Your phone number must be informed")
    private String mobile;

    @Basic
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;

    @Email(message = "Email invalid")
    private String email;

    @NotEmpty(message = "Your password must be informed")
    @Size(min = 3, message = "Your password must be at least 3 characters long")
    private String password;

    @NotEmpty(message = "The login must be informed")
    @Size(min = 4, message = "Your login must be at least 4 characters long")
    private String login;

    private boolean enable;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="userLaundry_role",
            joinColumns = @JoinColumn(name = "userLaundry_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;


    public void setIsEnable(boolean isEnable) {
    }
}

