//package com.github.mhzhou95.javaSpringBootTemplate.model;
//
//import com.pholser.junit.quickcheck.Property;
//import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
//import org.junit.runner.RunWith;
//
//import javax.validation.constraints.NotNull;
//
//import static org.hamcrest.Matchers.greaterThan;
//import static org.junit.Assume.assumeThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//
//// https://examples.javacodegeeks.com/core-java/junit/junit-quickcheck-example/
//// https://pholser.github.io/junit-quickcheck/site/0.9.4/usage/getting-started.html
//
////Have to add this to the beginning to use the Quick check
//@RunWith(JUnitQuickcheck.class)
//public class OrganizationTest {
//
//
//    //This means that it will do the test 5 times with different parameters
//
//    @Property(trials = 5)
//    public void getId(String username, String password, String firstName, String lastName, String email, String userRole, String phoneNumber) {
//        User user = new User(username, password, firstName, lastName, email, userRole, phoneNumber);
//        //assumeThat is from junit. greaterThan is from hamcrest.
//        //The assume that requires a matcher. and it will make it so that
//        //any parameters less than 0 is not included as a failed test.
////        assumeThat(num, greaterThan(0));
//        assertEquals(user.getFirstName(), firstName);
//        System.out.println(firstName);
//
//    }
//
//}