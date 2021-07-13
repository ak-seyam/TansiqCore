package io.asiam.tansiq.services.student_rank;

import io.asiam.tansiq.models.Major;
import io.asiam.tansiq.models.Request;
import io.asiam.tansiq.models.Student;
import io.asiam.tansiq.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentRankServiceImpl implements StudentRankService{
    private final RequestRepository requestRepository;

    @Autowired
    public StudentRankServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public Major getTheNextMajor(Student student, Major currentMajor) {
        // get the request made by student to this major // get the rank
        int rank = requestRepository.getByStudentIdAndMajorId(student.getId(), currentMajor.getId()).getRank();
        // find the request with the next rank
        Request req = requestRepository.getByStudentIdAndRank(student.getId(), rank+1);
        // get the major of that request
        return req != null ? req.getMajor() : null;
    }
}
