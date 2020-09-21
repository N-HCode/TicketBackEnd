package com.github.mhzhou95.javaSpringBootTemplate.repository;

import com.github.mhzhou95.javaSpringBootTemplate.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsernameEqualsAndPasswordEquals(String username, String password);
    User findByUsernameEquals(String username);
}
