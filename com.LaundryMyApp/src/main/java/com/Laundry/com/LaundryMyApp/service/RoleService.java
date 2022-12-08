package com.Laundry.com.LaundryMyApp.service;


import com.Laundry.com.LaundryMyApp.model.Role;

import java.util.List;

public interface RoleService {
    public Role searchRoleById(Long id);
    public Role searchRole(String role);
    public List<Role> roleList();
}
