package com.github.ticketProject.javaSpringBootTemplate.service;

import com.github.ticketProject.javaSpringBootTemplate.model.Permission;
import com.github.ticketProject.javaSpringBootTemplate.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import static com.github.ticketProject.javaSpringBootTemplate.authorization.Permissions.*;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;


    }

    public Permission findPermissionByName(String permissionName){
         Permission permission = permissionRepository.findByPermissionNameEquals(permissionName);
         if (permission == null){
             return null;
         }
         return permission;
    }

//    private void createInitialPermissions(){
//        new Permission(EVERYTHING.getPermissionName());
//        new Permission(USER_MODIFY.getPermissionName());
//        new Permission(USER_CREATE.getPermissionName());
//        new Permission(USER_DELETE.getPermissionName());
//        new Permission(USER_READ.getPermissionName());
//
//    }
}
