package com.openclassrooms.starterjwt.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.TestSecurityConfig;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@email.com");
        testUser.setPassword("password123");
    }

    @Test
    void testFindUserById_ShouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        User user = userService.findById(1L);
        assertNotNull(user);
    }

    @Test
    void testDeleteUser_ShouldCallRepositoryDelete() {
        doNothing().when(userRepository).deleteById(1L);
        userService.delete(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
