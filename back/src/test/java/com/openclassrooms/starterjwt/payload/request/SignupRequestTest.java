package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SignupRequestTest {

    @Test
    void testSignupRequestSettersAndGetters() {
        SignupRequest signupRequest = new SignupRequest();

        signupRequest.setEmail("signup@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("securePassword");

        assertEquals("signup@example.com", signupRequest.getEmail());
        assertEquals("John", signupRequest.getFirstName());
        assertEquals("Doe", signupRequest.getLastName());
        assertEquals("securePassword", signupRequest.getPassword());
    }
    @Test
    void testEqualsHashCodeAndToString() {
        SignupRequest req1 = new SignupRequest();
        req1.setEmail("a@b.com");
        req1.setFirstName("John");
        req1.setLastName("Doe");
        req1.setPassword("pass123");

        SignupRequest req2 = new SignupRequest();
        req2.setEmail("a@b.com");
        req2.setFirstName("John");
        req2.setLastName("Doe");
        req2.setPassword("pass123");

        // Test equals and hashCode
        assertEquals(req1, req2);
        assertEquals(req1.hashCode(), req2.hashCode());

        // Test toString contains field values
        String toString = req1.toString();
        assertTrue(toString.contains("a@b.com"));
        assertTrue(toString.contains("John"));
        assertTrue(toString.contains("Doe"));
        assertTrue(toString.contains("pass123"));

        // Test canEqual
        assertTrue(req1.canEqual(req2));
    }
}
