package io.asiam.tansiq.services.student_rank;

import io.asiam.tansiq.models.Major;
import io.asiam.tansiq.models.Student;

public interface StudentRankService {
    Major getTheNextMajor(Student student, Major currentMajor);
}
