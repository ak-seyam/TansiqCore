package io.asiam.tansiq.controllers;

import io.asiam.tansiq.models.Request;
import io.asiam.tansiq.services.request_builder.RequestBuilderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/students")
@RequiredArgsConstructor
public class StudentRoutes {
    private final RequestBuilderService requestBuilderService;
    @PostMapping("/requests")
    public List<Request> setStudentRequests(@RequestBody Map<String, Object> body) {
        return requestBuilderService.build((String) body.get("studentId"),
                (List<Map<String, Object>>) body.get("majors"));
    }
}
