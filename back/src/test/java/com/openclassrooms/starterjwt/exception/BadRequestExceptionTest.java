package com.openclassrooms.starterjwt.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BadRequestExceptionTest {

    @Test
    void shouldInstantiateException() {
        BadRequestException exception = new BadRequestException();
        assertNotNull(exception);
    }
}
