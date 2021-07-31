package io.asiam.tansiq.controllers;

import io.asiam.tansiq.exceptions.UserInputException;
import io.asiam.tansiq.models.Admin;
import io.asiam.tansiq.models.Major;
import io.asiam.tansiq.models.MajorInfo;
import io.asiam.tansiq.models.Student;
import io.asiam.tansiq.repositories.AdminsRepository;
import io.asiam.tansiq.repositories.MajorInfoRepository;
import io.asiam.tansiq.repositories.MajorRepository;
import io.asiam.tansiq.repositories.StudentRepository;
import io.asiam.tansiq.services.AssignerService;
import io.asiam.tansiq.services.validators.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/admins")
@RequiredArgsConstructor
@Slf4j
public class AdminRoutes {
    private final AdminsRepository adminsRepository;
    private final ValidationService validationService;
    private final StudentRepository studentRepository;
    private final MajorRepository majorRepository;
    private final MajorInfoRepository majorInfoRepository;
    private final AssignerService assignerService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("")
    public String hello() {
        return "Hello admin";
    }

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> createAdmin(@RequestBody Map<String, Object> body) {
        validationService.validatorWrapper(() -> {
                    validationService.validateEmail((String) body.get("email"),
                            email -> adminsRepository.getByEmail(email) == null);
                    validationService.validatePassword((String) body.get("password"));
                }
        );
        try {
            Admin a = adminsRepository.save(new Admin((String) body.get("email"), passwordEncoder.encode((String) body.get("password"))));
            return new ResponseEntity<>(
                    Map.of("success", true, "email", a.getEmail(), "id", a.getId()),
                    new HttpHeaders(),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UserInputException(e.getMessage());
        }
    }

    @PostMapping("/students")
    public ResponseEntity<Map<String, Object>> createStudent(@RequestBody Map<String, Object> body) {
        validationService.validatorWrapper(() -> {
            validationService.validateEmail((String) body.get("email"), (e) -> {
                return studentRepository.getByEmail(e) == null;
            });
            validationService.validatePassword((String) body.get("password"));
            validationService.validateName((String) body.get("name"));
            validationService.validateMark((Integer) body.get("mark"));
        });
        try {
            Student s = new Student(
                    (String) body.get("name"),
                    (Integer) body.get("mark"),
                    (String) body.get("email"),
                    passwordEncoder.encode((String) body.get("password"))
            );
            Student res = studentRepository.save(s);
            return new ResponseEntity<>(
                    Map.of("success", true,
                            "email", res.getEmail(),
                            "id", res.getId(),
                            "name", res.getName(),
                            "mark", s.getMark()),
                    new HttpHeaders(),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UserInputException(e.getMessage());
        }
    }

    @PostMapping("/majors")
    public @ResponseBody
    Major majorCreation(@RequestBody Map<String, Object> major) {
        String name = (String) major.get("name");
        Integer limit = (Integer) major.get("limit");

        validationService.validatorWrapper(() -> {
            validationService.validateMajorName(name);
            validationService.validateMajorLimit(limit);
        });

        try {
            Major majorSaved = majorRepository.save(new Major(name, limit));
            MajorInfo majorInfo = majorInfoRepository.save(new MajorInfo(majorSaved));
            return majorSaved;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UserInputException(e.getMessage());
        }
    }

    @PostMapping("/tansiq")
    public ResponseEntity<Map<String, Boolean>> doTansiq() {
        try {
            assignerService.assign();
            return new ResponseEntity<>(
                    Map.of("success", true),
                    new HttpHeaders(),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            log.error("Error while assignment: {}", e.getMessage());
            throw new UserInputException(e.getMessage());
        }
    }
}
