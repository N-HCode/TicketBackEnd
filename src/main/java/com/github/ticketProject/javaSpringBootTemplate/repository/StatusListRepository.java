package com.github.ticketProject.javaSpringBootTemplate.repository;

import com.github.ticketProject.javaSpringBootTemplate.model.StatusList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusListRepository extends CrudRepository<StatusList, Long>{
}
