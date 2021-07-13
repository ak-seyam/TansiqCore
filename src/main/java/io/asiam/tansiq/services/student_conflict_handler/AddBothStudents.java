package io.asiam.tansiq.services.student_conflict_handler;

import io.asiam.tansiq.models.Major;
import io.asiam.tansiq.models.Result;
import io.asiam.tansiq.models.Student;
import io.asiam.tansiq.repositories.MajorRepository;
import io.asiam.tansiq.repositories.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("addBothStudents")
public class AddBothStudents implements  LowestMarkConflictHandler{
    private final MajorRepository majorRepository;
    private final ResultRepository resultRepository;

    @Autowired
    public AddBothStudents(MajorRepository majorRepository, ResultRepository resultRepository) {
        this.majorRepository = majorRepository;
        this.resultRepository = resultRepository;
    }

    @Override
    public void handle(List<Student> studentList, Major major) {
        studentList.forEach(student -> {
            Result res = new Result();
            res.setMajor(major);
            res.setStudent(student);
            resultRepository.save(res);
        });
        major.setLimit(major.getLimit()+1);
        majorRepository.save(major);
    }
}
