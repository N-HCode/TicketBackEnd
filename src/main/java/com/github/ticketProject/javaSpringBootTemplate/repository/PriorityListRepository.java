package com.github.ticketProject.javaSpringBootTemplate.repository;

import com.github.ticketProject.javaSpringBootTemplate.model.PriorityList;
import org.springframework.data.repository.CrudRepository;

public interface PriorityListRepository extends CrudRepository<PriorityList, Long> {
}
