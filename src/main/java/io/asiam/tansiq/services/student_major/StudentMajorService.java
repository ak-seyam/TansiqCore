package io.asiam.tansiq.services.student_major;

import io.asiam.tansiq.models.*;
import io.asiam.tansiq.repositories.MajorInfoRepository;
import io.asiam.tansiq.repositories.MajorRepository;
import io.asiam.tansiq.repositories.RequestRepository;
import io.asiam.tansiq.repositories.ResultRepository;
import io.asiam.tansiq.services.least_student_info.LeastStudentService;
import io.asiam.tansiq.services.student_conflict_handler.LowestMarkConflictHandler;
import io.asiam.tansiq.services.student_no_request.StudentNoRequestResolver;
import io.asiam.tansiq.services.student_rank.StudentRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentMajorService {
    @Autowired
    @Qualifier("addBothStudents")
    private LowestMarkConflictHandler lowestMarkConflictHandler;
    @Autowired
    @Qualifier("throwIllegalStateException")
    private StudentNoRequestResolver studentNoRequestResolver;
    private final MajorInfoRepository majorInfoRepository;
    private final ResultRepository resultRepository;
    private final StudentRankService studentRankService;
    private final MajorRepository majorRepository;
    private final LeastStudentService leastStudentService;
    private final RequestRepository requestRepository;

    @Autowired
    public StudentMajorService(RequestRepository requestRepository,
                               LeastStudentService leastStudentService,
                               MajorRepository majorRepository,
                               StudentRankService studentRankService,
                               MajorInfoRepository majorInfoRepository,
                               ResultRepository resultRepository) {
        this.majorInfoRepository = majorInfoRepository;
        this.resultRepository = resultRepository;
        this.studentRankService = studentRankService;
        this.majorRepository = majorRepository;
        this.leastStudentService = leastStudentService;
        this.requestRepository = requestRepository;
    }

    public Major getTheFirstMajor(Student student) {
        Request request = requestRepository.getByStudentIdAndRank(student.getId(), 1);
        if (request == null) {
            studentNoRequestResolver.resolve(student);
            return null;
        }
        return request.getMajor();
    }

    /**
     * Assign student to major (Assuming there will be enough majors for students)
     *
     * @param student
     * @param major
     */
    public void assign(Student student, Major major) {
        if (student.getId() == null) {
            throw new IllegalArgumentException("Student should have an id");
        }
        if (major.getId() == null) {
            throw new IllegalArgumentException("Major should have an id");
        }
        MajorInfo majorInfo = majorInfoRepository.getByMajorId(major.getId());
        // if this limit wasn't reached yet
        if (major.getStudentsCount() < major.getLimit()) {
            // add the student
            Result res = new Result();
            res.setStudent(student);
            res.setMajor(major);
            resultRepository.save(res);
            // increase major count
            major.setStudentsCount(major.getStudentsCount() + 1);
            majorRepository.save(major);
        } else { // major is already full
            // if student.mark is higher than the major lowest mark
            if (student.getMark() > (majorInfo.getLowestStudent() != null ? majorInfo.getLowestStudent().getMark() : -1)) {
                //      add  student to major in results
                Result res = new Result();
                res.setStudent(student);
                res.setMajor(major);
                resultRepository.save(res);
                //      if major.lowestStudent (ls) exist
                if (majorInfo.getLowestStudent() != null) {
                    //          remove the lowest student in major (ls)
                    resultRepository.deleteByStudentIdAndMajorId(majorInfo.getLowestStudent().getId(), major.getId());
                    //          set ls to its next major
                    Major nextMajor = studentRankService.getTheNextMajor(student, major);
                    assign(majorInfo.getLowestStudent(), nextMajor);
                }
            }
            // else if student.mark is lower than the major lowest mark
            else if (student.getMark() < majorInfo.getLowestStudent().getMark()) {
                //      assign student to its next major
                Major nextMajor = studentRankService.getTheNextMajor(student, major);
                assign(student, nextMajor);
            } else {
                //      else -> resolve conflict between this student and the other student
                lowestMarkConflictHandler.handle(List.of(student), major);
            }
        }        // update #students
        if (major.getStudentsCount() == major.getLimit()) {
            leastStudentService.setLeastStudent(major);
        }
    }
}
