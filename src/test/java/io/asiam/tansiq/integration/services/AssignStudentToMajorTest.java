package io.asiam.tansiq.integration.services;

import io.asiam.tansiq.models.*;
import io.asiam.tansiq.repositories.*;
import io.asiam.tansiq.services.student_major.StudentMajorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class AssignStudentToMajorTest {
    @Autowired
    private MajorInfoRepository majorInfoRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MajorRepository majorRepository;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private StudentMajorService studentMajorService;

    @AfterEach
    public void afterEach() {
        studentRepository.deleteAll();
        majorRepository.deleteAll();
        requestRepository.deleteAll();
        resultRepository.deleteAll();
    }

    @Test
    public void itShouldAssignStudentToMajorInCaseOfMajorFinishSecondHigher() {
        List<Student> students = List.of(
                studentRepository.save(new Student("ahmed", 410)),
                studentRepository.save(new Student("ahmed2", 420))
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
        // assign student 1 to its first major
        // in real scenario the major will be pulled from get by student and rank
        studentMajorService.assign(studentOneRequests.get(0).getStudent(), studentOneRequests.get(0).getMajor());
        // assign student 2 to its majors
        studentMajorService.assign(studentTwoRequests.get(0).getStudent(), studentOneRequests.get(0).getMajor());
        // check the results
        List<Result> results = resultRepository.findAll();
        results.forEach(result -> {
            if (result.getStudent().getId().equals(students.get(0).getId())) {
                assertThat(result.getMajor().getName()).isEqualTo(majors.get(1).getName());
            } else {
                assertThat(result.getMajor().getName()).isEqualTo(majors.get(0).getName());
            }
        });
    }

    @Test
    public void itShouldDealWithConflictsIsExpected() {
        List<Student> students = List.of(
                studentRepository.save(new Student("ahmed", 420)),
                studentRepository.save(new Student("ahmed2", 420))
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
        // assign student 1 to its first major
        // in real scenario the major will be pulled from get by student and rank
        studentMajorService.assign(studentOneRequests.get(0).getStudent(), studentOneRequests.get(0).getMajor());
        // assign student 2 to its majors
        studentMajorService.assign(studentTwoRequests.get(0).getStudent(), studentOneRequests.get(0).getMajor());
        // check the results
        List<Result> results = resultRepository.findAll();
        results.forEach(result -> {
            assertThat(result.getMajor().getName()).isEqualTo(majors.get(0).getName());
        });
        Major updatedMajor = majorRepository.getById(majors.get(0).getId());
        assertThat(updatedMajor.getLimit()).isEqualTo(2);
    }

    @Test
    public void itShouldAssignStudentToMajorInCaseOfMajorFinishFirstHigher() {
        List<Student> students = List.of(
                studentRepository.save(new Student("ahmed", 420)),
                studentRepository.save(new Student("ahmed2", 410))
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
        // assign student 1 to its first major
        // in real scenario the major will be pulled from get by student and rank
        studentMajorService.assign(studentOneRequests.get(0).getStudent(), studentOneRequests.get(0).getMajor());
        // assign student 2 to its majors
        studentMajorService.assign(studentTwoRequests.get(0).getStudent(), studentOneRequests.get(0).getMajor());
        // check the results
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
    public void itShouldThrowExceptionIfStudentIdIsNull() {
        Student student = new Student("student", 420);
        Major major = majorRepository.save(new Major("major1", 1));
        assertThatThrownBy(() -> {
                    studentMajorService.assign(student, major);
                }
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id");
    }

    @Test
    public void itShouldThrowExceptionIfMajorIdIsNull() {
        Student student = studentRepository.save(new Student("student", 420));
        Major major = new Major("major1", 1);
        assertThatThrownBy(() -> {
                    studentMajorService.assign(student, major);
                }
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id");
    }


}
