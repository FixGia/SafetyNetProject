package com.project.apisafetynet.Controller;

import com.project.apisafetynet.Service.PersonService;
import com.project.apisafetynet.model.Person;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    final PersonService personService;


    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/person")
    public Person getPerson(Person firstname, Person lastname) {
        return personService.getPersonByFirstNameAndLastName(firstname, lastname);

    }
    @DeleteMapping("/person")
    public Person deletePerson(Person firstname, Person lastname) {
        Person person = personService.getPersonByFirstNameAndLastName(firstname, lastname);
        return personService.deletePerson(person);
    }
    @PatchMapping("/person")
    public Person modifyPerson(Person firstname, Person lastname) {
        Person person = personService.getPersonByFirstNameAndLastName(firstname, lastname);
        return personService.savePerson(person);
    }

    }

