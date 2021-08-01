package io.asiam.tansiq.controllers;

import io.asiam.tansiq.models.Result;
import io.asiam.tansiq.repositories.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/results")
@RequiredArgsConstructor
public class ResultRoutes {
    private final ResultRepository resultRepository;
    @GetMapping("")
    public ResponseEntity<List<Result>> getResults() {
        return new ResponseEntity<>(
                resultRepository.findAll(),
                new HttpHeaders(),
                HttpStatus.OK
        );
    }
}
