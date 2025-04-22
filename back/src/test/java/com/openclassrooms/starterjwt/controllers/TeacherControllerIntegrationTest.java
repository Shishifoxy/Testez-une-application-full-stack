package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(username = "Admin", authorities = {"ADMIN"})
public class TeacherControllerIntegrationTest {

    @Autowired
    private TeacherController teacherController;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherRepository teacherRepository;

    @BeforeEach
    void cleanDatabase() {
        teacherRepository.deleteAll();
    }

    @Test
    void testFindGetAllTeachers_shouldReturnResponseOk() {
        // given
        Teacher realTeacher1 = new Teacher();
        realTeacher1.setFirstName("Mario");
        realTeacher1.setLastName("Rossi");
        realTeacher1.setCreatedAt(LocalDateTime.now());
        realTeacher1.setUpdatedAt(LocalDateTime.now());

        Teacher realTeacher2 = new Teacher();
        realTeacher2.setFirstName("Luigi");
        realTeacher2.setLastName("Verdi");
        realTeacher2.setCreatedAt(LocalDateTime.now());
        realTeacher2.setUpdatedAt(LocalDateTime.now());

        teacherRepository.save(realTeacher1);
        teacherRepository.save(realTeacher2);

        // when
        ResponseEntity<?> responseEntity = teacherController.findAll();

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        List<?> bodyList = (List<?>) responseEntity.getBody();
        assertEquals(2, bodyList.size());
    }

    @Test
    void testFindById_shouldReturnResponseOk() {
        // given
        Teacher realTeacher1 = new Teacher();
        realTeacher1.setFirstName("Mario");
        realTeacher1.setLastName("Rossi");
        realTeacher1.setCreatedAt(LocalDateTime.parse("2021-09-29T10:00:00"));
        realTeacher1.setUpdatedAt(LocalDateTime.parse("2021-09-29T10:00:00"));

        Teacher realTeacher2 = new Teacher();
        realTeacher2.setFirstName("Luigi");
        realTeacher2.setLastName("Verdi");
        realTeacher2.setCreatedAt(LocalDateTime.now());
        realTeacher2.setUpdatedAt(LocalDateTime.now());

        teacherRepository.save(realTeacher1);
        teacherRepository.save(realTeacher2);

        Long idToFind = realTeacher1.getId();

        // when
        ResponseEntity<?> responseEntity = teacherController.findById(String.valueOf(idToFind));

        // then
        Optional<Teacher> result = teacherRepository.findById(idToFind);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody().toString().contains(result.get().getFirstName()));
    }

    @Test
    void testFindById_shouldReturnNotFoundCode() {
        // given
        Teacher realTeacher1 = new Teacher();
        realTeacher1.setFirstName("Mario");
        realTeacher1.setLastName("Rossi");
        realTeacher1.setCreatedAt(LocalDateTime.now());
        realTeacher1.setUpdatedAt(LocalDateTime.now());

        Teacher realTeacher2 = new Teacher();
        realTeacher2.setFirstName("Luigi");
        realTeacher2.setLastName("Verdi");
        realTeacher2.setCreatedAt(LocalDateTime.now());
        realTeacher2.setUpdatedAt(LocalDateTime.now());

        teacherRepository.save(realTeacher1);
        teacherRepository.save(realTeacher2);

        // when
        ResponseEntity<?> responseEntity = teacherController.findById("999");

        // then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}
