package com.example.demo.unitTest;

import com.example.demo.Student.Student;
import com.example.demo.Student.StudentController;
import com.example.demo.Student.StudentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class StudentControllerTest extends ControllerTest {

    private StudentService studentService = mock(StudentService.class);
    Student student1 = new Student(1l,"herman1", "herman1@yahoo.com", LocalDate.now(), 0);
    Student student2 = new Student(2l,"herman2", "herman2@yahoo.com", LocalDate.now());


    @Override
    Object getController() {
        return new StudentController(studentService);
    }

    @Test
    public void getStudentsTest() {
        when(studentService.getStudent()).thenReturn(List.of(student1, student2));

        final var responseBody = webTestClient
                .get()
                .uri("/api/v1/student")
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBodyList(Student.class)
                .returnResult()
                .getResponseBody();

        assertThat(responseBody)
                .hasSize(2)
                .map(Student::getId)
                .containsExactly(student1.getId(), student2.getId());
    }

    @Test
    @DisplayName("Register a new students")
    public void registerNewStudentTest() {
        webTestClient
                .post()
                .uri("/api/v1/student")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(student1)
                .exchange()
                .expectStatus().isEqualTo(200);

        verify(studentService).addNewStudent(student1);

    }

    @Test
    @DisplayName("Delete a students")
    public void deleteStudentTest() {
        Long id = 6L;
        webTestClient
                .delete()
                .uri("/api/v1/student/" + id)
                .exchange()
                .expectStatus().isEqualTo(200);

        verify(studentService).deleteStudent(id);

    }

    @Test
    @DisplayName("Update a students")
    public void updateStudentTest() {

        Long id = 1L;
        String name = "herman1";
        String email = "herman1@yahoo.cm";

        webTestClient
                .put()
                .uri(
                        uriBuilder -> uriBuilder
                                .pathSegment("api", "v1", "student", student1.getId().toString())
                                .queryParam("name", name)
                                .queryParam("email", email)
                                .build()
                )
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(200);

        verify(studentService).updateStudent(id, name, email);
    }

}

