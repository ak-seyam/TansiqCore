package io.asiam.tansiq.integration.services;

import io.asiam.tansiq.models.Major;
import io.asiam.tansiq.models.Request;
import io.asiam.tansiq.models.Student;
import io.asiam.tansiq.repositories.MajorRepository;
import io.asiam.tansiq.repositories.RequestRepository;
import io.asiam.tansiq.repositories.StudentRepository;
import io.asiam.tansiq.services.student_rank.StudentRankService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StudentRankServiceTest {
    @Autowired
    StudentRankService studentRankService;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    MajorRepository majorRepository;
    @Autowired
    RequestRepository requestRepository;

    @AfterEach
    public void afterEach() {
        studentRepository.deleteAll();
        majorRepository.deleteAll();
        requestRepository.deleteAll();
    }

    @Test
    public void itShouldGetTheNextMajorForStudent() {
        Student student = studentRepository.save(new Student("test", 420, "asadhmed@ahmed.com","asd"));
        List<Major> majorsInstances = List.of(
                new Major("major1", 2),
                new Major("major2", 2)
        );

        List<Major> majors = majorsInstances.stream().map(major -> {
           return majorRepository.save(major);
        }).collect(Collectors.toList());

        List<Request> requests = List.of(
                new Request(student, majors.get(0), 1),
                new Request(student, majors.get(1), 2)
        );
        requests.forEach(req -> {
            requestRepository.save(req);
        });
        Major resultMajor = studentRankService.getTheNextMajor(student, majors.get(0));
        assertThat(resultMajor.getName()).isEqualTo("major2");
    }

    @Test
    public void itShouldReturnNullIfMajorWasLast() {
        Student student = studentRepository.save(new Student("test", 420, "ahmdaed@ahmed.com","asd"));
        List<Major> majorsInstances = List.of(
                new Major("major1", 2),
                new Major("major2", 2)
        );

        List<Major> majors = majorsInstances.stream().map(major -> {
            return majorRepository.save(major);
        }).collect(Collectors.toList());

        List<Request> requests = List.of(
                new Request(student, majors.get(0), 1)
        );
        requests.forEach(req -> {
            requestRepository.save(req);
        });
        Major resultMajor = studentRankService.getTheNextMajor(student, majors.get(0));
        assertThat(resultMajor).isNull();
    }


}
