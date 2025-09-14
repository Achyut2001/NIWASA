package com.tdit.dataprovideservice.constants;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RegexPatternsTest {

    private void invokePrivateConstructor() throws Exception {
        Constructor<RegexPatterns> constructor = RegexPatterns.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    void testRegexPatterns() throws Exception {
        invokePrivateConstructor();

        assertTrue(Pattern.matches(RegexPatterns.PINCODE, "123456"));
        assertTrue(Pattern.matches(RegexPatterns.LAT_LONG, "-45.67"));
        assertTrue(Pattern.matches(RegexPatterns.HOST_ID, "123"));
        assertTrue(Pattern.matches(RegexPatterns.HOST_CONTACT, "9876543210"));
        assertTrue(Pattern.matches(RegexPatterns.EMAIL, "test@mail.com"));
        assertTrue(Pattern.matches(RegexPatterns.BASE_PRICE, "1200.50"));
        assertTrue(Pattern.matches(RegexPatterns.CURRENCY, "USD"));
        assertTrue(Pattern.matches(RegexPatterns.PROPERTY_URL, "https://example.com"));
        assertTrue(Pattern.matches(RegexPatterns.DATE_TIME, "2023-12-31 23:59:59"));
    }
}
