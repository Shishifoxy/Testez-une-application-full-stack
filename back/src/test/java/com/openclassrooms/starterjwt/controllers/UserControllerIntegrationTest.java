package com.openclassrooms.starterjwt.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.TestSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;

@SpringBootTest
@Import(TestSecurityConfig.class)
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        testUser = new User();
        testUser.setEmail("test@email.com");
        testUser.setFirstName("test");
        testUser.setLastName("test");
        testUser.setPassword("password123");
        userRepository.save(testUser);
    }

    @Test
    void testGetUserById_ShouldReturnUser_WhenExists() throws Exception {
        Optional<User> savedUser = userRepository.findAll().stream().findFirst();
        assert savedUser.isPresent();
        Long userId = savedUser.get().getId();

        mockMvc.perform(get("/api/user/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@email.com"))
                .andExpect(jsonPath("$.lastName").value("test"));
    }
}