package io.asiam.tansiq.services.least_student_info;

import io.asiam.tansiq.models.Major;
import io.asiam.tansiq.models.Student;

public interface LeastStudentService {
    Student getLeastStudent(Major major);
    void setLeastStudent(Major major);
}
