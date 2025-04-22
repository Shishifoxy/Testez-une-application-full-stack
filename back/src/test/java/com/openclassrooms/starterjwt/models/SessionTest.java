package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class SessionTest {

    @Test
    public void testSessionFields() {
        Session session = new Session();
        session.setId(1L);
        session.setName("Yoga");
        session.setDate(new Date());
        session.setDescription("Relaxing session");
        session.setUsers(new ArrayList<>());
        session.setCreatedAt(LocalDateTime.now());
        session.setUpdatedAt(LocalDateTime.now());

        assertEquals(1L, session.getId());
        assertEquals("Yoga", session.getName());
        assertEquals("Relaxing session", session.getDescription());
        assertNotNull(session.getDate());
        assertNotNull(session.getUsers());
        assertNotNull(session.getCreatedAt());
        assertNotNull(session.getUpdatedAt());
    }

    @Test
    public void testSessionBuilder() {
        LocalDateTime now = LocalDateTime.now();
        Date date = new Date();

        Session session = Session.builder()
                .id(2L)
                .name("Pilates")
                .date(date)
                .description("Intense session")
                .users(new ArrayList<>())
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertEquals(2L, session.getId());
        assertEquals("Pilates", session.getName());
        assertEquals("Intense session", session.getDescription());
        assertEquals(date, session.getDate());
        assertNotNull(session.getUsers());
        assertEquals(now, session.getCreatedAt());
        assertEquals(now, session.getUpdatedAt());
    }

    @Test
    public void testEqualsAndHashCode() {
        Date date = new Date();
        Session session1 = new Session(1L, "Yoga", date, "Relaxing session", null, new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());
        Session session2 = new Session(1L, "Yoga", date, "Relaxing session", null, new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());

        assertEquals(session1, session2);
        assertEquals(session1.hashCode(), session2.hashCode());
    }

    @Test
    public void testToString() {
        Session session = new Session();
        session.setId(3L);
        session.setName("Stretching");
        session.setDate(new Date());
        session.setDescription("Calm and slow session");
        session.setUsers(new ArrayList<>());
        session.setCreatedAt(LocalDateTime.now());
        session.setUpdatedAt(LocalDateTime.now());

        String toString = session.toString();
        assertTrue(toString.contains("Stretching"));
        assertTrue(toString.contains("Calm and slow session"));
    }
}