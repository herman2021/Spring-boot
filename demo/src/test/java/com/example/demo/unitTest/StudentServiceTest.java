package com.example.demo.unitTest;

import com.example.demo.Student.Student;
import com.example.demo.Student.StudentRepository;
import com.example.demo.Student.StudentService;
import com.sun.istack.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    Student student1 = mock(Student.class);
    Student student2 = mock((Student.class));
    private StudentRepository studentRepository = mock(StudentRepository.class);
    private final StudentService objectToTest = new StudentService(studentRepository);

    @Test
    @DisplayName("getStudent test ")
    void getStudentTest(){

        when(studentRepository.findAll()).thenReturn(List.of(student1, student2));

        List<Student> student = objectToTest.getStudent();
        assertThat(student)
                .hasSize(2)
                .contains(student1, student2);
    }

    @Test
    void deleteStudenSuccessFulltTest(){

        when(student1.getId()).thenReturn(1l);
        when(studentRepository.existsById(student1.getId())).thenReturn(true);

        objectToTest.deleteStudent(student1.getId());

        verify(studentRepository).deleteById(student1.getId());
    }

    @Test
    void deleteStudentNotFoundTest(){

        when(student1.getId()).thenReturn(1l);
        when(studentRepository.existsById(student1.getId())).thenReturn(false);

        assertThatThrownBy(
                ()-> objectToTest.deleteStudent(student1.getId())
        )
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasMessage(" student with id " + student1.getId() + " does not exist ");
    }

    @Test
    void updateStudentSuccessFulTest(){

        when(studentRepository.findById(1l)).thenReturn(Optional.of(student1));

        final var herman = "herman";
        final var email = "toto@yahoo.com";
        objectToTest.updateStudent(1l, herman, email);

        verify(student1).setName(herman);
        verify(student1).setEmail(email);
        verify(studentRepository).save(student1);
    }

    @Test
    void updateStudentFailedIdTest(){
        when(studentRepository.findById(2l)).thenReturn(Optional.empty());
        assertThatThrownBy(
                ()->objectToTest.updateStudent(2l, "herman", "toto@yahoo.com")
        )
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasMessage("student with id" + student1.getId() + " does not exist");
    }

    @Test
    void updateStudentFailedEmail(){
        when(studentRepository.findById(2l)).thenReturn(Optional.of(student1));
        when(studentRepository.findStudentByEmail("toto@yahoo.com")).thenReturn(Optional.of(student2));

        assertThatThrownBy(
                ()->objectToTest.updateStudent(2l, "herman", "toto@yahoo.com")
        )
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasMessage("email taken");
    }
}
