package com.openclassrooms.starterjwt.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.security.TestSecurityConfig;
import com.openclassrooms.starterjwt.services.SessionService;
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
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


import java.util.Date;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionService sessionService;

    private ObjectMapper objectMapper;
    private Session testSession;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        testSession = new Session();
        testSession.setId(1L);
    }

    @Test
    void testGetSessionById_ShouldReturnSession_WhenExists() throws Exception {
        when(sessionService.getById(1L)).thenReturn(testSession);

        mockMvc.perform(get("/api/session/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindAllSessions_ShouldReturnList() throws Exception {
        mockMvc.perform(get("/api/session"))
                .andExpect(status().isOk());
    }
    @Test
    void testCreateSession_ShouldReturnCreatedSession() throws Exception {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("New session");
        sessionDto.setDescription("Test desc");
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(1L);

        Session createdSession = new Session();
        createdSession.setId(2L);
        createdSession.setName("New session");
        createdSession.setDescription("Test desc");
        createdSession.setDate(sessionDto.getDate());
        createdSession.setTeacher(new Teacher().setId(1L));

        when(sessionService.create(any(Session.class))).thenReturn(createdSession);

        mockMvc.perform(post("/api/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteSession_ShouldReturnOk_WhenSessionExists() throws Exception {
        Session session = new Session();
        session.setId(1L);

        when(sessionService.getById(1L)).thenReturn(session);
        doNothing().when(sessionService).delete(1L);

        mockMvc.perform(delete("/api/session/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testParticipate_ShouldReturnOk_WhenValid() throws Exception {
        doNothing().when(sessionService).participate(1L, 1L);

        mockMvc.perform(post("/api/session/1/participate/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testNoLongerParticipate_ShouldReturnOk_WhenValid() throws Exception {
        doNothing().when(sessionService).noLongerParticipate(1L, 1L);

        mockMvc.perform(delete("/api/session/1/participate/1"))
                .andExpect(status().isOk());
    }
    @Test
    void testParticipate_ShouldReturnBadRequest_WhenInvalidId() throws Exception {
        mockMvc.perform(post("/api/session/invalid/participate/invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testParticipate_ShouldReturnNotFound_WhenNotFoundException() throws Exception {
        doThrow(new NotFoundException()).when(sessionService).participate(1L, 1L);

        mockMvc.perform(post("/api/session/1/participate/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testNoLongerParticipate_ShouldReturnBadRequest_WhenInvalidId() throws Exception {
        mockMvc.perform(delete("/api/session/invalid/participate/invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testNoLongerParticipate_ShouldReturnNotFound_WhenNotFoundException() throws Exception {
        doThrow(new NotFoundException()).when(sessionService).noLongerParticipate(1L, 1L);

        mockMvc.perform(delete("/api/session/1/participate/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteSession_ShouldReturnNotFound_WhenSessionIsNull() throws Exception {
        when(sessionService.getById(999L)).thenReturn(null);

        mockMvc.perform(delete("/api/session/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteSession_ShouldReturnBadRequest_WhenInvalidId() throws Exception {
        mockMvc.perform(delete("/api/session/invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFindById_ShouldReturnBadRequest_WhenInvalidId() throws Exception {
        mockMvc.perform(get("/api/session/invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFindById_ShouldReturnNotFound_WhenSessionIsNull() throws Exception {
        when(sessionService.getById(123L)).thenReturn(null);

        mockMvc.perform(get("/api/session/123"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdate_ShouldReturnBadRequest_WhenInvalidId() throws Exception {
        SessionDto dto = new SessionDto();
        mockMvc.perform(put("/api/session/invalid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateSession_ShouldReturnUpdatedSession_WhenValidRequest() throws Exception {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setId(1L);
        sessionDto.setName("Spring Boot Session");
        sessionDto.setDate(new Date());
        sessionDto.setDescription("Formation sur Spring Boot");
        sessionDto.setTeacher_id(1L);

        Session updatedSession = new Session();
        updatedSession.setId(1L);
        updatedSession.setName("Spring Boot Session");
        updatedSession.setDate(sessionDto.getDate());
        updatedSession.setDescription("Formation sur Spring Boot");
        updatedSession.setTeacher(new Teacher().setId(1L));

        when(sessionService.update(eq(1L), any(Session.class))).thenReturn(updatedSession);

        mockMvc.perform(put("/api/session/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isOk());
    }

}