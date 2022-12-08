package com.Laundry.com.LaundryMyApp.service;

import java.util.List;
import java.util.Optional;

import com.Laundry.com.LaundryMyApp.model.Role;
import com.Laundry.com.LaundryMyApp.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role searchRoleById(Long id) {
        Optional<Role> opt = roleRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new IllegalArgumentException("Role id : " + id + " doesn't exist");
        }
    }

    @Override
    public Role searchRole(String role) {
        Role rl = roleRepository.findByRole(role);
        return rl;
    }

    @Override
    public List<Role> roleList() {
        List<Role> roles = roleRepository.findAll();
        return roles;
    }

}
