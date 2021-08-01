package io.asiam.tansiq.services;

import io.asiam.tansiq.models.Student;
import io.asiam.tansiq.repositories.MajorInfoRepository;
import io.asiam.tansiq.repositories.ResultRepository;
import io.asiam.tansiq.repositories.StudentRepository;
import io.asiam.tansiq.services.results_service.ResultsService;
import io.asiam.tansiq.services.student_major.StudentMajorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignerService {
    private final StudentRepository studentRepository;
    private final StudentMajorService studentMajorService;
    private final ResultsService resultsService;
    public void assign() {
        resultsService.preMatchingCleanup();
        // get all students
        List<Student> studentList = studentRepository.findAll();
        studentList.forEach(student -> {
            studentMajorService.assign(student, studentMajorService.getTheFirstMajor(student));
        });
    }
}
