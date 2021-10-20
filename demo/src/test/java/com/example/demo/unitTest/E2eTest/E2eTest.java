package com.example.demo.unitTest.E2eTest;

import com.example.demo.DemoApplication;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;

@AutoConfigureMockMvc
@ActiveProfiles
@SpringJUnitConfig
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {DemoApplication.class}
)
public abstract class E2eTest {

    @LocalServerPort
    private int port;
    protected WebTestClient webTestClient;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init(){

        webTestClient = MockMvcWebTestClient.bindTo(mockMvc).build();
    }
}
