package com.tdit.dataprovideservice.constants;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorResponseKeysTest {

    private void invokePrivateConstructor() throws Exception {
        Constructor<ErrorResponseKeys> constructor = ErrorResponseKeys.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    void testErrorResponseKeys() throws Exception {
        invokePrivateConstructor();

        assertEquals("timestamp", ErrorResponseKeys.TIMESTAMP);
        assertEquals("status", ErrorResponseKeys.STATUS);
        assertEquals("error", ErrorResponseKeys.ERROR);
        assertEquals("message", ErrorResponseKeys.MESSAGE);
    }
}
