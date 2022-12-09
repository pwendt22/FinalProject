package com.Laundry.com.LaundryMyApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.Laundry.com.LaundryMyApp.model.Role;
import com.Laundry.com.LaundryMyApp.model.UserLaundry;
import com.Laundry.com.LaundryMyApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder criptografia;

    @Override
    public void deleteUserId(Long id) {
        UserLaundry userLaundry = searchUserById(id);
        userRepository.delete(userLaundry);
    }

    @Override
    public UserLaundry searchUserById(Long id) {
        Optional<UserLaundry> opt = userRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new IllegalArgumentException("Id user : " + id + " doesn't exist");
        }
    }

    @Override
    public UserLaundry searchUserByLogin(String login) {
        UserLaundry userLaundry = userRepository.findByLogin(login);
        return userLaundry;
    }

    @Override
    public UserLaundry saveUser(UserLaundry userLaundry) {
        Role role = roleService.searchRole("USER");
        List<Role> roles = new ArrayList<Role>();
        roles.add(role);
        userLaundry.setRoles(roles); // setting the user's role

        String criptPassword = criptografia.encode(userLaundry.getPassword());
        userLaundry.setPassword(criptPassword);

        return userRepository.save(userLaundry);
    }

    @Override
    public void altUser(UserLaundry userLaundry) {
        userRepository.save(userLaundry);
    }

    @Override
    public List<UserLaundry> userList() {
        List<UserLaundry> usersLaundry = userRepository.findAll();
        return usersLaundry;
    }

    @Override
    public void assignRoleToUser(long idUser, int[] idsRoles, boolean isEnable) {
        List<Role> roles = new ArrayList<Role>();
        for (int i = 0; i < idsRoles.length; i++) {
            long idRole = idsRoles[i];
            Role role = roleService.searchRoleById(idRole);
            roles.add(role);
        }
        UserLaundry userLaundry = searchUserById(idUser);
        userLaundry.setRoles(roles);
        userLaundry.setEnable(isEnable);
        altUser(userLaundry);
    }

}
