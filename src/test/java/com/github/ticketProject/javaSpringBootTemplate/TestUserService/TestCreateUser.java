package com.github.ticketProject.javaSpringBootTemplate.TestUserService;
import com.github.ticketProject.javaSpringBootTemplate.model.Organization;
import com.github.ticketProject.javaSpringBootTemplate.model.User;
import com.github.ticketProject.javaSpringBootTemplate.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestCreateUser {

    // Create the service and let Spring auto set it up
    @Autowired
    private UserService service;

    // Create the test method
    public void testCreateUser(String username, String password, String firstName, String lastName, String email, String userRole, String phoneNumber) throws Exception {

        // Create a new User Object using predefined roles
        User newUser = new User(username, password, firstName, lastName, email, userRole, phoneNumber);
        Organization organization = new Organization();
        // Ask the service to create the user to the database
        service.createUser(organization.getUsersList(), newUser);

        User createdUser = service.getUserByUsername(newUser.getUsername());

        // Check all fields in the created user
        Assert.assertEquals(createdUser.getUsername(), username);
        System.out.println(createdUser.getUsername());

        Assert.assertEquals(createdUser.getPassword(), password);
        System.out.println(createdUser.getPassword());

        Assert.assertEquals(createdUser.getFirstName(), firstName);
        System.out.println(createdUser.getFirstName());

        Assert.assertEquals(createdUser.getLastName(), lastName);
        System.out.println(createdUser.getLastName());

        Assert.assertEquals(createdUser.getEmail(), email);
        System.out.println(createdUser.getEmail());

        Assert.assertEquals(createdUser.getUserRole(), userRole);
        System.out.println(createdUser.getUserRole());

        Assert.assertEquals(createdUser.getPhoneNumber(), phoneNumber);
        System.out.println(createdUser.getPhoneNumber());

    }

    // Create Test cases
    @Test
    public void test1() throws Exception {
        testCreateUser("username", "password",  "minghao",  "zhou",  "1234@gmail.com",  "ADMIN",  "277-211-5555");
    }
}

