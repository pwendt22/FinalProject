package com.Laundry.com.LaundryMyApp.repository;

import com.Laundry.com.LaundryMyApp.model.UserLaundry;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<UserLaundry, Long> {
    UserLaundry findByLogin(String login);
}