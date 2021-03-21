package com.github.ticketProject.javaSpringBootTemplate.model;


import com.pholser.junit.quickcheck.generator.java.lang.Encoded;
import net.jqwik.api.ForAll;
import net.jqwik.api.From;
import net.jqwik.api.Property;
import static org.junit.jupiter.api.Assertions.*;

class OrganizationContactTest {

    @Property(tries = 10)
    void getOrganizationContactName(@ForAll @Encoded.InCharset("UTF-8") String string) {
        String a = string;
        assertEquals(a,string);
        System.out.println(string);
    }
}