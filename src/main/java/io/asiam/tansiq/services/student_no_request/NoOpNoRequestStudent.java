package io.asiam.tansiq.services.student_no_request;

import io.asiam.tansiq.models.Student;
import org.springframework.stereotype.Service;

@Service("noOpNoRequestStudent")
public class NoOpNoRequestStudent implements StudentNoRequestResolver{
    @Override
    public void resolve(Student student) {
        // does nothing
    }
}
