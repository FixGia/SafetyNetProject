package com.project.apisafetynet.Controller;

import com.project.apisafetynet.Service.PersonService;
import com.project.apisafetynet.model.Person;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
public class PersonController {

    final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Create - Add a new person
     * @param person An object person
     * @return The person object saved
     */
    @PostMapping("/person")
    public Person createPerson(@RequestBody Person person) {
        return personService.savePerson(person);
    }
    /**
     * Read - Get one person
     * @param firstname The firstname of the person
     * @param lastname The lastname of the person
     * @return A person object full filled
     */

    @GetMapping("/person/{firstname}+{lastname}")
    public Person getPerson(@PathVariable("firstname")Person firstname,@PathVariable("lastname") Person lastname) {
        Optional<Person> person = personService.getPersonByFirstNameAndLastName(firstname, lastname);
        if(person.isPresent()) {
            return person.get();
        } else {
            return null;
        }
    }





    }

