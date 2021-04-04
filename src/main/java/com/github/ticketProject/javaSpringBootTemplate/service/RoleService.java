package com.github.ticketProject.javaSpringBootTemplate.service;

import com.github.ticketProject.javaSpringBootTemplate.model.Permission;
import com.github.ticketProject.javaSpringBootTemplate.model.Role;
import com.github.ticketProject.javaSpringBootTemplate.model.User;
import com.github.ticketProject.javaSpringBootTemplate.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;


    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;

    }


    public Role findRoleByName(String roleName){

        Role role = roleRepository.findByRoleNameEquals(roleName);
        if (role == null){
            return null;
        }
        return  role;

    }

    public boolean addPermissionToRole(String roleName, Permission permission){
        Role role = findRoleByName(roleName);
        if (role == null){
            return false;
        }
        role.addPermission(permission);

        return true;

    }

}
