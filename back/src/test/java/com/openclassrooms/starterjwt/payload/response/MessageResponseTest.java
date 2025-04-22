package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageResponseTest {

    @Test
    void testConstructorAndGetter() {
        MessageResponse response = new MessageResponse("Success");
        assertEquals("Success", response.getMessage());
    }

    @Test
    void testSetter() {
        MessageResponse response = new MessageResponse("Initial");
        response.setMessage("Updated");
        assertEquals("Updated", response.getMessage());
    }
}
