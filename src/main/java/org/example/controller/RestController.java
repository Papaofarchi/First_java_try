package org.example.controller;


import org.example.service.GeneralPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    private GeneralPersonService generalPersonService;
    @GetMapping("/api/v1/persons/run")
    public void run() {
        System.out.println("Application started successfully");
        generalPersonService.init();
    }

}
