package com.github.mhzhou95.javaSpringBootTemplate.service;

import com.github.mhzhou95.javaSpringBootTemplate.model.Person;
import com.github.mhzhou95.javaSpringBootTemplate.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {
    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
        Person person1 = new Person("John", "Doe");
        Person person2 = new Person("Robin", "Hood");
        Person person3 = new Person("Jack", "Sparrow");

        this.createPerson(person1);
        this.createPerson(person2);
        this.createPerson(person3);
    }

    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(Long id) {
//        Optional<Person> person = personRepository.findById(id);
        Person person = personRepository.findById(id).orElse(new Person());
        return person;
    }
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    public Person delete(Long id) {
        Optional<Person> person = personRepository.findById(id);
        personRepository.deleteById(id);
        return person.get();
    }

    public Person editPerson(Long id, Person person) {
        Person optionalPerson = this.findById(id);
        Person personBefore = optionalPerson;

            if(person.getFirstName() != null){
                personBefore.setFirstName(person.getFirstName());
            }
            if(person.getLastName() != null){
                personBefore.setLastName(person.getLastName());
            }
            return personRepository.save(personBefore);
    }
}
