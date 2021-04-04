package com.github.ticketProject.javaSpringBootTemplate.service;

import com.github.ticketProject.javaSpringBootTemplate.model.Permission;
import com.github.ticketProject.javaSpringBootTemplate.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import static com.github.ticketProject.javaSpringBootTemplate.authorization.Roles.*;
import static com.github.ticketProject.javaSpringBootTemplate.authorization.Permissions.*;


@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;

//        initialSetup();
    }

    public Permission findPermissionByName(String permissionName){
         Permission permission = permissionRepository.findByPermissionNameEquals(permissionName);
         if (permission == null){
             return null;
         }
         return permission;
    }

    public Permission createPermission(String permissionName){
        Permission permission = new Permission(permissionName);
        return permissionRepository.save(permission);
    }

//    private void initialSetup(){
//
//        ROOT.getRoleInEnum().addPermission(EVERYTHING.getPermission());
//        ADMIN.getRoleInEnum()
//                .addPermission(USER_READ.getPermission());
//        ADMIN.getRoleInEnum()
//                .addPermission(USER_MODIFY.getPermission());
//        ADMIN.getRoleInEnum()
//                .addPermission(USER_CREATE.getPermission());
//        ADMIN.getRoleInEnum()
//                .addPermission(USER_DELETE.getPermission());
//        USER.getRoleInEnum().addPermission(USER_READ.getPermission());
//
//    }
}
