package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.controller.dto.person.PersonDto;
import org.example.service.person.GeneralPersonService;
import org.example.service.person.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.example.service.person.GeneralPersonService.PERSON_DTO;

@Controller
@Slf4j
@SessionAttributes({"personDto", "personChatDto"})
@RequestMapping("api/v1")
public class PersonController {
    @Autowired
    private GeneralPersonService generalPerson;
    @Autowired
    private PhoneService phone;

    @GetMapping("/person/form")
    public String showPersonForm(Model model) {
       return generalPerson.showPersonForm(model);
    }

    @PostMapping("/person/add")
    public String addPerson(@Valid @ModelAttribute(PERSON_DTO) PersonDto personDto, Model model) {
        return generalPerson.addPerson(personDto, model);
    }

    @PostMapping("/phone-details/request")
    @ResponseBody
    public String setPhoneDetails() {
        return phone.setPhoneDetails();
    }
}
