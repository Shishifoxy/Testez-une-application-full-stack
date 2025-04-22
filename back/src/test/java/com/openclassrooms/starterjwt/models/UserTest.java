package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserFields() {
        LocalDateTime now = LocalDateTime.now();

        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setPassword("securepassword");
        user.setAdmin(true);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        assertEquals(1L, user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("Doe", user.getLastName());
        assertEquals("John", user.getFirstName());
        assertEquals("securepassword", user.getPassword());
        assertTrue(user.isAdmin());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }

    @Test
    public void testUserBuilder() {
        LocalDateTime now = LocalDateTime.now();

        User user = User.builder()
                .id(2L)
                .email("builder@example.com")
                .lastName("Smith")
                .firstName("Jane")
                .password("builderpass")
                .admin(false)
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertEquals(2L, user.getId());
        assertEquals("builder@example.com", user.getEmail());
        assertEquals("Smith", user.getLastName());
        assertEquals("Jane", user.getFirstName());
        assertEquals("builderpass", user.getPassword());
        assertFalse(user.isAdmin());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }

    @Test
    public void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        User user1 = new User(1L, "equal@example.com", "Doe", "John", "pass", true, now, now);
        User user2 = new User(1L, "equal@example.com", "Doe", "John", "pass", true, now, now);

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testToString() {
        LocalDateTime now = LocalDateTime.now();

        User user = new User();
        user.setId(3L);
        user.setEmail("string@example.com");
        user.setLastName("White");
        user.setFirstName("Jessie");
        user.setPassword("tostring");
        user.setAdmin(false);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        String str = user.toString();
        assertTrue(str.contains("string@example.com"));
        assertTrue(str.contains("White"));
        assertTrue(str.contains("Jessie"));
    }
}
