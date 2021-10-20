package com.example.demo.unitTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringJUnitConfig
abstract class ControllerTest {

    protected WebTestClient webTestClient;

    @BeforeEach
    public void initWebTestClient() {
        webTestClient = WebTestClient.bindToController(getController())
                .build();
    }

     abstract Object getController();
}

