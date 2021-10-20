package com.example.demo.unitTest.E2eTest;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class StudentDeleteById extends E2eTest{
    @Test
    void execute() {
        Long id1 = Long.MAX_VALUE - 1;

        jdbcTemplate.update("DELETE FROM student");
        jdbcTemplate.update(
                "INSERT INTO student (id, name, email, dob) VALUES (?, ?, ?, ?)",
                new Object[]{id1, "cedric", "cedric1@lao-sarl.cm", LocalDate.now()}
        );

        webTestClient
                .delete()
                .uri("/api/v1/student/"+id1)
                .exchange()
                .expectStatus().isOk();

        final var students = jdbcTemplate.queryForList("SELECT * FROM student WHERE id ="+id1);

        assertThat(students)
                .isEmpty();
    }
}
