package com.github.ticketProject.javaSpringBootTemplate.repository;

import com.github.ticketProject.javaSpringBootTemplate.model.Organization;
import com.github.ticketProject.javaSpringBootTemplate.model.User;
import com.github.ticketProject.javaSpringBootTemplate.model.UsersList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByUsernameEqualsAndPasswordEquals(String username, String password);
    User findByUsernameEquals(String username);
    User findByFullNameEquals(String fullName);

//    User findByUserIdIn(Ticket ticket);

    Iterable<User> findAllByUsersListEquals(UsersList usersList);

    Page<User> findAllByUsersListEquals(UsersList usersList, Pageable pageable);
}
