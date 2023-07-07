package org.example.controller;


import org.example.service.GeneralPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    private GeneralPersonService general;
    @GetMapping("/api/v1/persons/run")
    @ResponseBody
    public String run() {
        System.out.println("Application started successfully");
        general.init();
        return "Fuck you";
    }

}
