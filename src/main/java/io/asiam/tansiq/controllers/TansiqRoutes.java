package io.asiam.tansiq.controllers;

import io.asiam.tansiq.models.Result;
import io.asiam.tansiq.repositories.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tansiq")
@RequiredArgsConstructor
public class TansiqRoutes {
    private final ResultRepository resultRepository;

    @GetMapping("")
    public List<Result> getResults() {
        return resultRepository.findAll();
    }
}
