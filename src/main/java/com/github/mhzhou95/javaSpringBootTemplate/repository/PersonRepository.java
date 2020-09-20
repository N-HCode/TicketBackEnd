package com.github.mhzhou95.javaSpringBootTemplate.repository;

import com.github.mhzhou95.javaSpringBootTemplate.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

}
