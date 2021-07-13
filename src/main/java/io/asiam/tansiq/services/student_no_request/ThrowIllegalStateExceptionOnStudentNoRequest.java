package io.asiam.tansiq.services.student_no_request;

import io.asiam.tansiq.models.Student;
import org.springframework.stereotype.Service;

@Service("throwIllegalStateException")
public class ThrowIllegalStateExceptionOnStudentNoRequest implements StudentNoRequestResolver{
    @Override
    public void resolve(Student student) {
        throw new IllegalStateException("Student " + student.getId() + " doesn't have a first rank major");
    }
}
