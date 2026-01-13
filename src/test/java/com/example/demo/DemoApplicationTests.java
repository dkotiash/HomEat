package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")  // <-- Diese Zeile ist wichtig!
class DemoApplicationTests {

    @Test
    void contextLoads() {
        // Prüft, ob Spring Context lädt
    }

}
