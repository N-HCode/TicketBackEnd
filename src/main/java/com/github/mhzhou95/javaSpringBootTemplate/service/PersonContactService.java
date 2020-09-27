package com.github.mhzhou95.javaSpringBootTemplate.service;

import com.github.mhzhou95.javaSpringBootTemplate.repository.PersonContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonContactService {

    private PersonContactRepository personContactRepository;

    @Autowired
    public PersonContactService(PersonContactRepository personContactRepository) {
        this.personContactRepository = personContactRepository;
    }
}
