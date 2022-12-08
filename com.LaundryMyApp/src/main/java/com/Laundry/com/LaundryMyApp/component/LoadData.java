package com.Laundry.com.LaundryMyApp.component;


import com.Laundry.com.LaundryMyApp.model.Role;
import com.Laundry.com.LaundryMyApp.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



@Component
public class LoadData implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        String[] roles = {"ADMIN", "USER", "STAFF"};

        for (String roleString: roles) {
            Role role = roleRepository.findByRole(roleString);
            if (role == null) {
                role = new Role(roleString);
                roleRepository.save(role);
            }
        }
    }

}
