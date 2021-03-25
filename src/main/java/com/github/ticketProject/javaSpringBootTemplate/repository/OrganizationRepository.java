package com.github.ticketProject.javaSpringBootTemplate.repository;

import com.github.ticketProject.javaSpringBootTemplate.model.Organization;
import com.github.ticketProject.javaSpringBootTemplate.model.User;
import com.github.ticketProject.javaSpringBootTemplate.model.UsersList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Long>{
    //Types of Repository
    //CrudRepository
    //PagingAndSortingRepository extends crud
    //JpaRepository extends Paging

    //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods

    //for Repository the name is very specific.
    //Breakdown
    //findby[Then your Property name Exactyl]Contains
    //If the property name is wrong, there will be an error message stating that it cannot be found in the model.
    Optional<Organization> findByUsersListContains(UsersList usersList);
}
