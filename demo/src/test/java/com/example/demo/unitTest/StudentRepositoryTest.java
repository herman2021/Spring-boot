package com.example.demo.unitTest;

import com.example.demo.Student.Student;
import com.example.demo.Student.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTest {
    @Autowired
    StudentRepository studentRepository;
    Student student1 = new Student("herman1", "herman1@yahoo.com", LocalDate.now());
    Student student2 = new Student("herman2", "herman2@yahoo.com", LocalDate.now());
    Student student3 = new Student("herman3", "herman3@yahoo.com", LocalDate.now());
    private List<Long> allStudentId;

    @Test
    public void crudTest() {
        final var allStudentId = studentRepository.saveAll(List.of(student1, student2))
                .stream()
                .map(Student::getId)
                .collect(Collectors.toList());
        assertThat(allStudentId)
                .isNotEmpty()
                .hasSize(2);

        final var studentResult = studentRepository.findStudentByEmail("herman1@yahoo.com")
                .get();
        assertThat(studentResult)
                .isNotNull()
                .isEqualTo(student1);

        final var id = studentRepository.save(student3).getId();
        assertThat(id)
                .isNotNull()
                .isEqualTo(student3.getId());

        final  var existById = studentRepository.existsById(id);
        assertThat(id).isEqualTo(true);

        studentRepository.deleteById(id);
        assertThat(studentRepository.existsById(id)).isFalse();
    }
}
