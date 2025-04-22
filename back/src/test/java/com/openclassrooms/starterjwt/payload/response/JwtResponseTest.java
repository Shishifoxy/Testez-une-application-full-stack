package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtResponseTest {

    @Test
    void testAllFields() {
        JwtResponse jwt = new JwtResponse("abc123", 1L, "user1", "John", "Doe", true);

        assertEquals("abc123", jwt.getToken());
        assertEquals("Bearer", jwt.getType());
        assertEquals(1L, jwt.getId());
        assertEquals("user1", jwt.getUsername());
        assertEquals("John", jwt.getFirstName());
        assertEquals("Doe", jwt.getLastName());
        assertTrue(jwt.getAdmin());
    }

    @Test
    void testSetters() {
        JwtResponse jwt = new JwtResponse("token", 1L, "user", "Jane", "Smith", false);

        jwt.setToken("newToken");
        jwt.setType("Custom");
        jwt.setId(99L);
        jwt.setUsername("newUser");
        jwt.setFirstName("Alice");
        jwt.setLastName("Brown");
        jwt.setAdmin(true);

        assertEquals("newToken", jwt.getToken());
        assertEquals("Custom", jwt.getType());
        assertEquals(99L, jwt.getId());
        assertEquals("newUser", jwt.getUsername());
        assertEquals("Alice", jwt.getFirstName());
        assertEquals("Brown", jwt.getLastName());
        assertTrue(jwt.getAdmin());
    }
}
