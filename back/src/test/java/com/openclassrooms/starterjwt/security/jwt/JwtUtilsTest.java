package com.openclassrooms.starterjwt.security.jwt;

import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtUtilsTest {

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetailsImpl userPrincipal;

    @InjectMocks
    private JwtUtils jwtUtils;

    private final String jwtSecret = "openclassrooms";

    @BeforeEach
    void setUp() {
        // Injecte la clé secrète dans l'instance de JwtUtils
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", jwtSecret);
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", 3600000); // 1h
    }

    @Test
    void generateJwtToken_ValidAuthentication_GeneratesToken() {
        // given
        UserDetails userDetails = new UserDetailsImpl(1L, "testuser", "testuser", "testuser", true, "openclassrooms");

        when(authentication.getPrincipal()).thenReturn(userPrincipal);
        when(userPrincipal.getUsername()).thenReturn(userDetails.getUsername());

        // when
        String token = jwtUtils.generateJwtToken(authentication);

        // then
        assertNotNull(token);
        assertTrue(token.split("\\.").length == 3);
    }

    @Test
    void getUserNameFromJwtToken_ValidToken_ReturnsUsername() {
        // given
        String username = "testuser";
        String token = generateValidToken(username);

        // when
        String extractedUsername = jwtUtils.getUserNameFromJwtToken(token);

        // then
        assertEquals(username, extractedUsername);
    }

    @Test
    void validateJwtToken_ValidToken_ReturnsTrue() {
        String validToken = generateValidToken("testuser");

        boolean isValid = jwtUtils.validateJwtToken(validToken);

        assertTrue(isValid);
    }

    @Test
    void validateJwtToken_InvalidSignature() {
        String invalidSignatureToken = generateValidToken("testuser") + "tampered";

        boolean isValid = jwtUtils.validateJwtToken(invalidSignatureToken);

        assertFalse(isValid);
    }

    @Test
    void validateJwtToken_InvalidToken() {
        String invalidToken = "invalidToken";

        boolean isValid = jwtUtils.validateJwtToken(invalidToken);

        assertFalse(isValid);
    }

    @Test
    void validateJwtToken_ExpiredToken() {
        String expiredToken = generateExpiredToken();

        boolean isValid = jwtUtils.validateJwtToken(expiredToken);

        assertFalse(isValid);
    }

    @Test
    void validateJwtToken_UnsupportedToken() {
        String unsupportedToken = generateUnsupportedToken();

        boolean isValid = jwtUtils.validateJwtToken(unsupportedToken);

        assertFalse(isValid);
    }

    @Test
    void validateJwtToken_EmptyClaims() {
        String emptyClaimsToken = Jwts.builder()
                .setSubject("testuser")
                .signWith(SignatureAlgorithm.HS512, "your_secret_key")
                .compact();

        boolean isValid = jwtUtils.validateJwtToken(emptyClaimsToken);

        assertFalse(isValid);
    }


    private String generateValidToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    private String generateValidToken() {
        return generateValidToken("testuser");
    }

    private String generateExpiredToken() {
        return Jwts.builder()
                .setSubject("testuser")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() - 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    private String generateUnsupportedToken() {
        return Jwts.builder()
                .setSubject("testuser")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .compact();
    }
}
