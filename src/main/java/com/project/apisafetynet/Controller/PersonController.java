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
     *
     * @param firstname The firstname of the person
     * @param lastname  The lastname of the person
     * @return A person object full filled
     */
    @GetMapping("/person/{firstname}+{lastname}")
    public Person getPerson(@PathVariable("firstname") Person firstname, @PathVariable("lastname") Person lastname) {
        Optional<Person> person = personService.getPersonByFirstNameAndLastName(firstname, lastname);
        return person.orElse(null);
    }

    /**
     * Update - Update an existing person
     *
     * @param firstname - The firstname of the person to update
     * @param lastname  - The lastname of the person to update
     * @param person    - The person object is updated
     * @return person - The person object updated
     */
    @PutMapping("person/{firstname}+{lastname}")
    public Person updatePerson(@PathVariable("firstname") Person firstname, @PathVariable("lastname") Person lastname, @RequestBody Person person) {
        Optional<Person> p = personService.getPersonByFirstNameAndLastName(firstname, lastname);
        if (p.isPresent()) {
            Person currentPerson = p.get();

            String email = person.getEmail();
            if (email != null) {
                currentPerson.setEmail(email);
            }
            String address = person.getAddress();
            if (address != null) {
                currentPerson.setAddress(address);
            }
            String city = person.getCity();
            if (city != null) {
                currentPerson.setCity(city);
            }
            String zip = person.getZip();
            if (zip != null) {
                currentPerson.setZip(zip);
            }
            String phone = person.getPhone();
            if (phone != null) {
                currentPerson.setPhone(phone);
            }
            personService.savePerson(currentPerson);
            return currentPerson;
        } else {
            return null;
        }
    }

    /**
     *
     * @param firstname The firstname of the person to delete
     * @param lastname  - The lastname of the person to delete
     * @param person - The person object to delete
     * @return method to delete person
     */

    @DeleteMapping("person/{firstname}+{lastname}")
    public Person deletePerson(@PathVariable("firstname") Person firstname, @PathVariable("lastname") Person lastname, @RequestBody Person person) {
        personService.getPersonByFirstNameAndLastName(firstname, lastname);
        return personService.deletePerson(person);
    }
}


