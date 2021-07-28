package io.asiam.tansiq.controllers;

import org.springframework.web.bind.annotation.*;

@RestController()
public class Ping {
    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
}
