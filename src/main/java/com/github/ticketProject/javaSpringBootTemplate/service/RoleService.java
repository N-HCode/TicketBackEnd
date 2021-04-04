package com.github.ticketProject.javaSpringBootTemplate.service;

import com.github.ticketProject.javaSpringBootTemplate.model.Permission;
import com.github.ticketProject.javaSpringBootTemplate.model.Role;
import com.github.ticketProject.javaSpringBootTemplate.repository.PermissionRepository;
import com.github.ticketProject.javaSpringBootTemplate.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.github.ticketProject.javaSpringBootTemplate.authorization.Permissions.*;
import static com.github.ticketProject.javaSpringBootTemplate.authorization.Permissions.USER_READ;
import static com.github.ticketProject.javaSpringBootTemplate.authorization.Roles.*;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;


    @Autowired
    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        initialSetup();
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

    private void initialSetup(){

        permissionRepository.save(EVERYTHING.getPermission());
        permissionRepository.save(USER_READ.getPermission());
        permissionRepository.save(USER_MODIFY.getPermission());
        permissionRepository.save(USER_CREATE.getPermission());
        permissionRepository.save(USER_DELETE.getPermission());

        ROOT.getRoleInEnum().addPermission(EVERYTHING.getPermission());
        ADMIN.getRoleInEnum()
                .addPermission(USER_READ.getPermission());
        ADMIN.getRoleInEnum()
                .addPermission(USER_MODIFY.getPermission());
        ADMIN.getRoleInEnum()
                .addPermission(USER_CREATE.getPermission());
        ADMIN.getRoleInEnum()
                .addPermission(USER_DELETE.getPermission());
        USER.getRoleInEnum().addPermission(USER_READ.getPermission());

        roleRepository.save(ROOT.getRoleInEnum());
        roleRepository.save(ADMIN.getRoleInEnum());
        roleRepository.save(USER.getRoleInEnum());


    }

}
