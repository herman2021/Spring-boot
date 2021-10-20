package com.example.demo.unitTest.E2eTest;

import com.example.demo.Student.Student;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;
import static org.mockito.Mockito.verify;

public class StudentUpdate extends E2eTest{
    @Test
    void execute() {
        Long id2 = Long.MAX_VALUE - 2;
        String name = "herman";
        String email = "herman@yahoo.com";

        jdbcTemplate.update("DELETE FROM student");
        jdbcTemplate.update(
                "INSERT INTO student (id, name, email, dob) VALUES (?, ?, ?, ?)",
                new Object[]{id2, "herman", "herman@yahoo.com", LocalDate.now()}
        );

        webTestClient
                .put()
                .uri(
                        uriBuilder -> uriBuilder
                                .pathSegment("api", "v1", "student", id2.toString())
                                .queryParam("name", name)
                                .queryParam("email", email)
                                .build()
                )
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(200);

        final var students = jdbcTemplate.queryForList("SELECT * FROM student WHERE id ="+id2);

        assertThat(students.get(0).get("name").equals(name));
        assertThat(students.get(0).get("email").equals(email));


    }
}
