package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TeacherTest {

    @Test
    public void testTeacherFields() {
        LocalDateTime now = LocalDateTime.now();

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setLastName("Doe");
        teacher.setFirstName("John");
        teacher.setCreatedAt(now);
        teacher.setUpdatedAt(now);

        assertEquals(1L, teacher.getId());
        assertEquals("Doe", teacher.getLastName());
        assertEquals("John", teacher.getFirstName());
        assertEquals(now, teacher.getCreatedAt());
        assertEquals(now, teacher.getUpdatedAt());
    }
    @Test
    public void testEqualsWithNullAndDifferentClass() {
        Teacher teacher = new Teacher();
        assertNotEquals(teacher, null); // comparaison avec null
        assertNotEquals(teacher, "some string"); // comparaison avec une autre classe
    }

    @Test
    public void testTeacherBuilder() {
        LocalDateTime now = LocalDateTime.now();

        Teacher teacher = Teacher.builder()
                .id(2L)
                .lastName("Smith")
                .firstName("Anna")
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertEquals(2L, teacher.getId());
        assertEquals("Smith", teacher.getLastName());
        assertEquals("Anna", teacher.getFirstName());
        assertEquals(now, teacher.getCreatedAt());
        assertEquals(now, teacher.getUpdatedAt());
    }

    @Test
    public void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        Teacher t1 = new Teacher(1L, "Doe", "Jane", now, now);
        Teacher t2 = new Teacher(1L, "Doe", "Jane", now, now);

        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    public void testToString() {
        LocalDateTime now = LocalDateTime.now();

        Teacher teacher = new Teacher();
        teacher.setId(3L);
        teacher.setLastName("Brown");
        teacher.setFirstName("Lisa");
        teacher.setCreatedAt(now);
        teacher.setUpdatedAt(now);

        String str = teacher.toString();
        assertTrue(str.contains("Brown"));
        assertTrue(str.contains("Lisa"));
    }
}
