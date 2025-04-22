package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginRequestTest {

    @Test
    void testLoginRequestSettersAndGetters() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        assertEquals("test@example.com", loginRequest.getEmail());
        assertEquals("password123", loginRequest.getPassword());
    }
}
