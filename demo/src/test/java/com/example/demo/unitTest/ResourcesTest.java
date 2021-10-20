package com.example.demo.unitTest;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringJUnitConfig
public abstract class ResourcesTest {

    protected WebTestClient webTestClient;

    @BeforeEach
    public void initWebTestClient(){
        webTestClient = WebTestClient.bindToController(getResources())
                .build();
    }

    protected abstract Object getResources();
}
