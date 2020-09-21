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
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
//        Optional<User> user = userRepository.findById(id);
        User user = userRepository.findById(id).orElse(null);
        return user;
    }
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User delete(Long id) {
        Optional<User> user = userRepository.findById(id);
        userRepository.deleteById(id);
        return user.get();
    }

    public User editUser(Long id, User user) {
        User optionalUser = this.findById(id);
        User userBefore = optionalUser;

            if(user.getFirstName() != null){
                userBefore.setFirstName(user.getFirstName());
            }
            if(user.getLastName() != null){
                userBefore.setLastName(user.getLastName());
            }
            return userRepository.save(userBefore);
    }
}
