package com.Laundry.com.LaundryMyApp.security;

import javax.transaction.Transactional;

import com.Laundry.com.LaundryMyApp.model.UserLaundry;
import com.Laundry.com.LaundryMyApp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserDetailServ implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailServ(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserLaundry userLaundry = userRepository.findByLogin(username);

        if(userLaundry != null && userLaundry.isEnable()) {
            UserDetails userDetails = new UserDetails(userLaundry);
            return userDetails;
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

}
