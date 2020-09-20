package com.github.mhzhou95.javaSpringBootTemplate.service;

import com.github.mhzhou95.javaSpringBootTemplate.model.User;
import com.github.mhzhou95.javaSpringBootTemplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        User user1 = new User("John", "Doe");
        User user2 = new User("Robin", "Hood");
        User user3 = new User("Jack", "Sparrow");

        this.createUser(user1);
        this.createUser(user2);
        this.createUser(user3);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
//        Optional<User> user = userRepository.findById(id);
        User person = userRepository.findById(id).orElse(new User());
        return person;
    }
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User delete(Long id) {
        Optional<User> user = userRepository.findById(id);
        userRepository.deleteById(id);
        return user.get();
    }

    public User editUser(Long id, User person) {
        User optionalUser = this.findById(id);
        User userBefore = optionalUser;

            if(person.getFirstName() != null){
                userBefore.setFirstName(person.getFirstName());
            }
            if(person.getLastName() != null){
                userBefore.setLastName(person.getLastName());
            }
            return userRepository.save(userBefore);
    }
}
