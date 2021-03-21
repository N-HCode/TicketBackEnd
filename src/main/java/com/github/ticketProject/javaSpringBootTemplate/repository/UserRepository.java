package com.github.ticketProject.javaSpringBootTemplate.repository;

import com.github.ticketProject.javaSpringBootTemplate.model.Organization;
import com.github.ticketProject.javaSpringBootTemplate.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsernameEqualsAndPasswordEquals(String username, String password);
    User findByUsernameEquals(String username);
    User findByFullNameEquals(String fullName);

//    User findByUserIdIn(Ticket ticket);

    Iterable<User> findAllByOrganizationEquals(Organization organization);
}
