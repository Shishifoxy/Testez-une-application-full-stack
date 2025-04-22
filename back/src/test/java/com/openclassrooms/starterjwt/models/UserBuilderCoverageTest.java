package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class UserBuilderCoverageTest {

    @Test
    public void shouldCoverAllBuilderMethods() {
        LocalDateTime now = LocalDateTime.now();

        User.UserBuilder builder = User.builder();
        builder.id(99L);
        builder.email("builder_coverage@example.com");
        builder.lastName("CoverageLast");
        builder.firstName("CoverageFirst");
        builder.password("builder123");
        builder.admin(true);
        builder.createdAt(now);
        builder.updatedAt(now);

        User user = builder.build();

        assertNotNull(user);
        assertEquals(99L, user.getId());
        assertEquals("builder_coverage@example.com", user.getEmail());
        assertEquals("CoverageLast", user.getLastName());
        assertEquals("CoverageFirst", user.getFirstName());
        assertEquals("builder123", user.getPassword());
        assertTrue(user.isAdmin());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }
}
