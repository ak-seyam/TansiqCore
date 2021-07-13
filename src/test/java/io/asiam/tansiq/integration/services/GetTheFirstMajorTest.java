package io.asiam.tansiq.integration.services;

import io.asiam.tansiq.models.Major;
import io.asiam.tansiq.models.Request;
import io.asiam.tansiq.models.Student;
import io.asiam.tansiq.repositories.MajorRepository;
import io.asiam.tansiq.repositories.RequestRepository;
import io.asiam.tansiq.repositories.StudentRepository;
import io.asiam.tansiq.services.student_major.StudentMajorService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GetTheFirstMajorTest {
    @Autowired
    StudentMajorService studentMajorService;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    MajorRepository majorRepository;
    @Autowired
    RequestRepository requestRepository;

    @AfterEach
    public void afterEach(){
        studentRepository.deleteAll();
    }

    @Test
    public void itShouldGetTheFirstMajorSuccessfully() {
        Student student = studentRepository.save(new Student("ahmed",420));
        List<Major> majors = List.of(
                majorRepository.save(new Major("hello", 1)),
                majorRepository.save(new Major("hello2", 2))
        );
        List<Request> request = List.of(
                requestRepository.save(new Request(student, majors.get(0),1)),
                requestRepository.save(new Request(student, majors.get(1),2))
        );
        assertThat(studentMajorService.getTheFirstMajor(student).getId()).isEqualTo(majors.get(0).getId());
    }
}
