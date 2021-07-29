package io.asiam.tansiq.services.request_builder;

import io.asiam.tansiq.exceptions.UserInputException;
import io.asiam.tansiq.models.Major;
import io.asiam.tansiq.models.Request;
import io.asiam.tansiq.models.Student;
import io.asiam.tansiq.repositories.MajorRepository;
import io.asiam.tansiq.repositories.RequestRepository;
import io.asiam.tansiq.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestBuilderService {
    private final StudentRepository studentRepository;
    private final MajorRepository majorRepository;
    private final RequestRepository requestRepository;

    /**
     * @param studentId
     * @param majorIds  [
     *                  {
     *                  majorId: "uuid",
     *                  rank : number
     *                  }, ...
     *                  ]
     * @return list of build requests
     */
    public List<Request> build(String studentId, List<Map<String, Object>> majorIds) {
        try {
            Student student = studentRepository.getById(UUID.fromString(studentId));
            List<Request> requests = majorIds.stream().map(mid -> {
                Major major = majorRepository.getById(UUID.fromString((String) mid.get("majorId")));
                return new Request(student, major, (Integer) mid.get("rank"));
            }).collect(Collectors.toList());
            return requestRepository.saveAll(requests);
        } catch (Exception e) {
            log.error("Can't create a request: {}", e.getMessage());
            throw new UserInputException(e.getMessage());
        }
    }
}
