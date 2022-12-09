package com.Laundry.com.LaundryMyApp.security;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.Laundry.com.LaundryMyApp.model.Role;
import com.Laundry.com.LaundryMyApp.model.UserLaundry;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;



public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private UserLaundry userLaundry;

    public UserDetails(UserLaundry userLaundry) {
        super();
        this.userLaundry = userLaundry;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> roles = userLaundry.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(Role role: roles) {
            SimpleGrantedAuthority sga = new SimpleGrantedAuthority(role.getRole());
            authorities.add(sga);
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return userLaundry.getPassword();
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return userLaundry.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return userLaundry.isEnable();
    }

    public String getName() {
        return userLaundry.getName();
    }

}
