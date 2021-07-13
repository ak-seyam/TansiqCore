package io.asiam.tansiq.services.least_student_info;

import io.asiam.tansiq.models.Major;
import io.asiam.tansiq.models.MajorInfo;
import io.asiam.tansiq.models.Student;
import io.asiam.tansiq.repositories.MajorInfoRepository;
import io.asiam.tansiq.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
@Transactional
public class LeastStudentImpl implements LeastStudentService {

    private final StudentRepository studentRepository;
    private final MajorInfoRepository majorInfoRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public LeastStudentImpl(StudentRepository studentRepository, MajorInfoRepository majorInfoRepository) {
        this.studentRepository = studentRepository;
        this.majorInfoRepository = majorInfoRepository;
    }

    @Override
    public Student getLeastStudent(Major major) {
//         get the student that has the same mark and requested that major
        Query query = entityManager.createNativeQuery(
                "SELECT NAME FROM STUDENT " +
                        "WHERE mark = (" +
                        "SELECT MIN(s.mark) FROM student s " +
                        "WHERE s.id IN (" +
                        "SELECT student_id FROM result " +
                        "WHERE major_id = :majorId)" +
                        ") AND ROWNUM = 1 " +
                        "AND ID IN (SELECT STUDENT_ID FROM REQUEST WHERE major_id = :majorId)"
        );
        query.setParameter("majorId", major.getId());
        String name = (String) query.getSingleResult();
        return studentRepository.getByName(name);
    }

    @Override
    public void setLeastStudent(Major major) {
        Student lowestStudent = getLeastStudent(major);
        MajorInfo updatedInfo = majorInfoRepository.getByMajorId(major.getId());
        updatedInfo.setLowestStudent(lowestStudent);
        majorInfoRepository.save(updatedInfo);
    }
}
