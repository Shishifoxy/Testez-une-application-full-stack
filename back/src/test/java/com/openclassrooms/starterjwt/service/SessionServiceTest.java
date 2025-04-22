package com.openclassrooms.starterjwt.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.TestSecurityConfig;
import com.openclassrooms.starterjwt.services.SessionService;
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
class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SessionService sessionService;

    private Session testSession;
    private User testUser;

    @BeforeEach
    void setUp() {
        testSession = new Session();
        testSession.setId(1L);
        testSession.setName("Yoga Matinal");

        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@email.com");
    }

    @Test
    void testGetById_ShouldReturnSession() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(testSession));
        Session session = sessionService.getById(1L);
        assertNotNull(session);
        assertEquals("Yoga Matinal", session.getName());
    }

    @Test
    void testCreateSession_ShouldReturnSavedSession() {
        when(sessionRepository.save(any(Session.class))).thenReturn(testSession);
        Session createdSession = sessionService.create(testSession);
        assertNotNull(createdSession);
        assertEquals("Yoga Matinal", createdSession.getName());
    }

    @Test
    void testFindAllSessions_ShouldReturnList() {
        when(sessionRepository.findAll()).thenReturn(Arrays.asList(testSession));
        List<Session> sessions = sessionService.findAll();
        assertFalse(sessions.isEmpty());
        assertEquals(1, sessions.size());
    }

    @Test
    void testUpdateSession_ShouldReturnUpdatedSession() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(testSession));
        when(sessionRepository.save(any(Session.class))).thenReturn(testSession);
        Session updatedSession = sessionService.update(1L, testSession);
        assertNotNull(updatedSession);
        assertEquals("Yoga Matinal", updatedSession.getName());
    }

    @Test
    void testDeleteSession_ShouldCallRepositoryDelete() {
        doNothing().when(sessionRepository).deleteById(1L);
        sessionService.delete(1L);
        verify(sessionRepository, times(1)).deleteById(1L);
    }

    @Test
    void testParticipate_ShouldThrowNotFoundException_WhenSessionNotFound() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(com.openclassrooms.starterjwt.exception.NotFoundException.class,
                () -> sessionService.participate(1L, 1L));
    }

    @Test
    void testNoLongerParticipate_ShouldThrowNotFoundException_WhenSessionNotFound() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(com.openclassrooms.starterjwt.exception.NotFoundException.class,
                () -> sessionService.noLongerParticipate(1L, 1L));
    }
}