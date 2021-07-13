package io.asiam.tansiq.services.student_conflict_handler;

import io.asiam.tansiq.models.Major;
import io.asiam.tansiq.models.Student;

import java.util.List;

public interface LowestMarkConflictHandler {
    public void handle(List<Student> studentList, Major major);
}
