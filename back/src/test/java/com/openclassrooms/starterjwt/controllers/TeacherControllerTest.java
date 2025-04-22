package com.openclassrooms.starterjwt.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.security.TestSecurityConfig;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    private Teacher testTeacher;

    @BeforeEach
    void setUp() {
        testTeacher = new Teacher();
        testTeacher.setId(1L);
    }

    @Test
    void testGetTeacherById_ShouldReturnTeacher_WhenExists() throws Exception {
        when(teacherService.findById(1L)).thenReturn(testTeacher);

        mockMvc.perform(get("/api/teacher/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindAllTeachers_ShouldReturnList() throws Exception {
        mockMvc.perform(get("/api/teacher"))
                .andExpect(status().isOk());
    }
}