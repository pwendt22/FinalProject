package com.Laundry.com.LaundryMyApp.repository;


import com.Laundry.com.LaundryMyApp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}
