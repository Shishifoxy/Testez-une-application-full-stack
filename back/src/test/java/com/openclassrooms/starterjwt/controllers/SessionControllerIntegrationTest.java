package com.openclassrooms.starterjwt.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.security.TestSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
class
SessionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SessionRepository sessionRepository;

    private ObjectMapper objectMapper;
    private Session testSession;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        sessionRepository.deleteAll();
        testSession = new Session();
        testSession.setName("Yoga Matinal");
        testSession.setDescription("Séance de yoga matinale pour bien démarrer la journée");
        testSession.setDate(new Date());
        testSession = sessionRepository.save(testSession);
    }

    @Test
    void testGetSessionById_ShouldReturnSession_WhenExists() throws Exception {
        Optional<Session> savedSession = sessionRepository.findAll().stream().findFirst();
        assert savedSession.isPresent();
        Long sessionId = savedSession.get().getId();

        mockMvc.perform(get("/api/session/" + sessionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Yoga Matinal"))
                .andExpect(jsonPath("$.description").value("Séance de yoga matinale pour bien démarrer la journée"))
                .andExpect(jsonPath("$.date").isNotEmpty());
    }
}
