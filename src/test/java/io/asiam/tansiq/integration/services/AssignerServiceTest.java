package io.asiam.tansiq.integration.services;

import io.asiam.tansiq.models.*;
import io.asiam.tansiq.repositories.*;
import io.asiam.tansiq.services.AssignerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class AssignerServiceTest {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    MajorRepository majorRepository;
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    MajorInfoRepository majorInfoRepository;
    @Autowired
    ResultRepository resultRepository;
    @Autowired
    AssignerService assignerService;

    @AfterEach
    public void afterEach() {
        studentRepository.deleteAll();
        majorRepository.deleteAll();
        requestRepository.deleteAll();
        resultRepository.deleteAll();
        majorInfoRepository.deleteAll();
    }

    @Test
    public void itShouldAssignStudentsCorrectly() {
        // given
        // create students
        List<Student> students = List.of(
                studentRepository.save(new Student("ahmed", 420, "ahmed@ah12med.com","s" )),
                studentRepository.save(new Student("ahmed2", 410, "ahmed@ahe23med.com", "x"))
        );

        List<Major> majors = List.of(
                majorRepository.save(new Major("major1", 1)),
                majorRepository.save(new Major("major2", 1))
        );

        // generate major info for each major
        List<MajorInfo> majorInfo = List.of(
                majorInfoRepository.save(new MajorInfo(majors.get(0))),
                majorInfoRepository.save(new MajorInfo(majors.get(1)))
        );

        // create requests for the first student
        List<Request> studentOneRequestsUnsaved = List.of(
                new Request(students.get(0), majors.get(0), 1),
                new Request(students.get(0), majors.get(1), 2)
        );

        List<Request> studentOneRequests = studentOneRequestsUnsaved.stream().map(request -> {
            return requestRepository.save(request);
        }).collect(Collectors.toList());

        // create requests for the second student
        List<Request> studentTwoRequestsUnsaved = List.of(
                new Request(students.get(1), majors.get(0), 1),
                new Request(students.get(1), majors.get(1), 2)
        );
        List<Request> studentTwoRequests = studentTwoRequestsUnsaved.stream().map(request -> {
            return requestRepository.save(request);
        }).collect(Collectors.toList());

        // when
        // assign
        assignerService.assign();

        // then
        // check results
        List<Result> results = resultRepository.findAll();
        results.forEach(result -> {
            if (result.getStudent().getId().equals(students.get(0).getId())) {
                assertThat(result.getMajor().getName()).isEqualTo(majors.get(0).getName());
            } else {
                assertThat(result.getMajor().getName()).isEqualTo(majors.get(1).getName());
            }
        });
    }

    @Test
    public void itShouldThrowExceptionIfNotAllStudentsHasRequests() {
        // given
        // create students
        List<Student> students = List.of(
                studentRepository.save(new Student("ahmed", 420, "ahmeimed@ahmed.com", "a")),
                studentRepository.save(new Student("ahmed2", 410, "ahmihihihihied@ahmed.com", "c"))
        );

        List<Major> majors = List.of(
                majorRepository.save(new Major("major1", 1)),
                majorRepository.save(new Major("major2", 1))
        );

        // generate major info for each major
        List<MajorInfo> majorInfo = List.of(
                majorInfoRepository.save(new MajorInfo(majors.get(0))),
                majorInfoRepository.save(new MajorInfo(majors.get(1)))
        );

        // create requests for the first student
        List<Request> studentOneRequestsUnsaved = List.of(
                new Request(students.get(0), majors.get(0), 1),
                new Request(students.get(0), majors.get(1), 2)
        );

        List<Request> studentOneRequests = studentOneRequestsUnsaved.stream().map(request -> {
            return requestRepository.save(request);
        }).collect(Collectors.toList());

        assertThatThrownBy(
                () -> {
                    // when
                    assignerService.assign();
                }
                // then
        ).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Student");

    }

}
