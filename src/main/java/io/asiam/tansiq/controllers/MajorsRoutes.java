package io.asiam.tansiq.controllers;

import io.asiam.tansiq.models.Major;
import io.asiam.tansiq.repositories.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/majors")
@RequiredArgsConstructor
public class MajorsRoutes {
    private final MajorRepository majorRepository;
    @GetMapping("")
    public ResponseEntity<List<Major>> getMajors() {
        List<Major> majors = majorRepository.findAll();
        return new ResponseEntity<>(
                majors,
                new HttpHeaders(),
                HttpStatus.OK
        );
    }
}
