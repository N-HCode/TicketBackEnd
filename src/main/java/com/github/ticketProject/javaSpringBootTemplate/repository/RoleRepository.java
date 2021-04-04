package com.github.ticketProject.javaSpringBootTemplate.repository;


import com.github.ticketProject.javaSpringBootTemplate.model.Role;
import com.github.ticketProject.javaSpringBootTemplate.model.Ticket;

import org.springframework.data.domain.Page;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

    Role findByRoleNameEquals(String roleName);

}
