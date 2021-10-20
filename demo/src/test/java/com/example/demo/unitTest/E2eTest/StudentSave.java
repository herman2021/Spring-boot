package com.example.demo.unitTest.E2eTest;

import com.example.demo.Student.Student;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentSave extends E2eTest{
    @Test
    void execute(){
        String name = "herman";
        String email = "herman@yahoo.com";
        Student student1 = new Student( "herman", "herman@yahoo.com", LocalDate.now());

        jdbcTemplate.update("DELETE FROM student");
        webTestClient
                .post()
                .uri("/api/v1/student")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(student1)
                .exchange()
                .expectStatus().isEqualTo(200);

        final var students = jdbcTemplate.queryForList("SELECT * FROM student");
        assertThat(students).hasSize(1);
        assertThat(students.get(0).get("name").equals(name));
        assertThat(students.get(0).get("email").equals(email));

    }

    public static class StudentGetAllTest extends E2eTest{

        @Test
        void execute(){
            Long id1 = Long.MAX_VALUE - 1;
            Long id2 = Long.MAX_VALUE - 2;

            jdbcTemplate.update("DELETE FROM student");
            jdbcTemplate.batchUpdate(
                    "INSERT INTO student(id, name, email, dob) VALUES (?, ?, ?, ?)",
                    List.of(
                            new Object[]{id1, "herman1", "herman1@yahoo.com", LocalDate.now()},
                            new Object[]{id2, "herman2", "herman2@yahoo.com", LocalDate.now()}
                    )
            );


            final var responseBody = webTestClient
                    .get()
                    .uri("/api/v1/student")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBodyList(Student.class)
                    .returnResult()
                    .getResponseBody();

            assertThat(responseBody)
                    .hasSize(2)
                    .map(Student::getId)
                    .containsExactly(id1, id2);
        }
    }
}
