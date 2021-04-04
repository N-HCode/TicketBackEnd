package com.github.ticketProject.javaSpringBootTemplate.repository;

import com.github.ticketProject.javaSpringBootTemplate.model.Permission;


import org.springframework.data.repository.CrudRepository;

public interface PermissionRepository extends CrudRepository<Permission, Integer> {

    Permission findByPermissionNameEquals(String permissionName);

}
