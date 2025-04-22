package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.security.TestSecurityConfig;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@email.com");
        testUser.setFirstName("test");
        testUser.setLastName("test");
    }

    @Test
    void testGetUserById_ShouldReturnBadRequest_WhenInvalidId() throws Exception {
        mockMvc.perform(get("/api/user/invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetUserById_ShouldReturnNotFound_WhenUserNotExists() throws Exception {
        when(userService.findById(999L)).thenReturn(null);

        mockMvc.perform(get("/api/user/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test@email.com")
    void testDeleteUser_ShouldSucceed_WhenValidRequestAndAuthorized() throws Exception {
        when(userService.findById(1L)).thenReturn(testUser);

        mockMvc.perform(delete("/api/user/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteUser_ShouldReturnBadRequest_WhenInvalidId() throws Exception {
        mockMvc.perform(delete("/api/user/invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "unauthorized@email.com")
    void testDeleteUser_ShouldReturnUnauthorized_WhenDifferentUser() throws Exception {
        when(userService.findById(1L)).thenReturn(testUser);

        mockMvc.perform(delete("/api/user/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test@email.com")
    void testDeleteUser_ShouldReturnNotFound_WhenUserNotExists() throws Exception {
        when(userService.findById(999L)).thenReturn(null);

        mockMvc.perform(delete("/api/user/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetUserById_ShouldReturnUser_WhenExists() throws Exception {
        when(userService.findById(1L)).thenReturn(testUser);

        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk());
    }
}
