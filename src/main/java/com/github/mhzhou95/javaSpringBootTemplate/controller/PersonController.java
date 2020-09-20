package com.github.mhzhou95.javaSpringBootTemplate.controller;

import com.github.mhzhou95.javaSpringBootTemplate.model.Person;
import com.github.mhzhou95.javaSpringBootTemplate.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/person")
public class PersonController {
    private PersonService service;

    @Autowired
    public PersonController(PersonService service) {
        this.service = service;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @GetMapping("/")
    public ResponseEntity<?> findAll(){
        Iterable<Person> allPeople = service.findAll();
        List<Person> list = new ArrayList<>();
        allPeople.forEach( (person)-> list.add(person));
        ResponseEntity<?> responseFindAll = new ResponseEntity<>(allPeople, HttpStatus.OK);
        return responseFindAll;
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        Person personById = service.findById(id);
        ResponseEntity responseFindId = new ResponseEntity(personById, HttpStatus.OK);
        return responseFindId;
    }
    @PostMapping("/")
    public ResponseEntity<?> createPerson(@RequestBody Person person){
        Person responseBody = service.createPerson(person);
        ResponseEntity<?> responseCreatePerson = new ResponseEntity<>(responseBody, HttpStatus.CREATED);
        return responseCreatePerson;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable Long id){
        Person responsePerson = service.delete(id);
        ResponseEntity<?> responseDeletePerson = new ResponseEntity(responsePerson, HttpStatus.OK);
        return responseDeletePerson;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editPerson(@PathVariable Long id, @RequestBody Person person){
        Person editedPerson = service.editPerson(id, person);
        ResponseEntity<?> responseEditPerson = new ResponseEntity<>(editedPerson, HttpStatus.OK);
        return responseEditPerson;
    }
}
