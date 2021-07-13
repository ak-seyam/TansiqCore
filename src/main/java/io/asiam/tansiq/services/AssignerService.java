package io.asiam.tansiq.services;

import io.asiam.tansiq.models.Student;
import io.asiam.tansiq.repositories.StudentRepository;
import io.asiam.tansiq.services.student_major.StudentMajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignerService {
    private final StudentRepository studentRepository;
    private final StudentMajorService studentMajorService;

    @Autowired
    public AssignerService(StudentRepository studentRepository, StudentMajorService studentMajorService) {
        this.studentRepository = studentRepository;
        this.studentMajorService = studentMajorService;
    }

    public void assign() {
        // get all students
        List<Student> studentList = studentRepository.findAll();
        studentList.forEach(student -> {
            studentMajorService.assign(student, studentMajorService.getTheFirstMajor(student));
        });
    }
}
