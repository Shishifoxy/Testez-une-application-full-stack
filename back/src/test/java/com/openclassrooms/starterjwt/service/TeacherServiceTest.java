package com.openclassrooms.starterjwt.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.security.TestSecurityConfig;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    private Teacher testTeacher;

    @BeforeEach
    void setUp() {
        testTeacher = new Teacher();
        testTeacher.setId(1L);
    }

    @Test
    void testFindTeacherById_ShouldReturnTeacher() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(testTeacher));
        Teacher teacher = teacherService.findById(1L);
        assertNotNull(teacher);
    }

    @Test
    void testFindAllTeachers_ShouldReturnList() {
        when(teacherRepository.findAll()).thenReturn(Arrays.asList(testTeacher));
        List<Teacher> teachers = teacherService.findAll();
        assertFalse(teachers.isEmpty());
        assertEquals(1, teachers.size());
    }
}
