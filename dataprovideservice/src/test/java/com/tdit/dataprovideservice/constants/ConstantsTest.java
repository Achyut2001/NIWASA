package com.tdit.dataprovideservice.constants;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {

    @Test
    void testPrivateConstructor() throws Exception {
        Constructor<Constants> constructor = Constants.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    void testAllConstantsAreNotNull() throws IllegalAccessException {
        Field[] fields = Constants.class.getDeclaredFields();

        for (Field field : fields) {

            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true);
                Object value = field.get(null);

                assertNotNull(value, "Constant " + field.getName() + " should not be null");

                if (value instanceof String) {
                    assertFalse(((String) value).isEmpty(), "Constant " + field.getName() + " should not be empty");
                }
            }
        }
    }
}
