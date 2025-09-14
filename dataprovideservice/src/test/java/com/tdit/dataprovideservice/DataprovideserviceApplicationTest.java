package com.tdit.dataprovideservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class DataprovideserviceApplicationTest {

    @Test
    void contextLoads() {

    }

    @Test
    void mainMethodRuns() {

        String[] args = {};
        DataprovideserviceApplication.main(args);
    }
}

