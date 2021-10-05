package com.project.apisafetynet.Controller;

import com.project.apisafetynet.Service.PersonService;
import com.project.apisafetynet.model.ModelRepository.Person;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("persons")
public class PersonController {

    final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Read - Get all persons
     *
     * @return all persons
     */
    @GetMapping()
    public Iterable<Person> getPersons() {
        return personService.getPersons();
    }

    /**
     * Create - Add a new person
     *
     * @param person An object person
     * @param Id     Person's firstName and lastName
     * @return The person object saved
     */
    @PostMapping("{Id}")
    public Person createPerson(@PathVariable("Id") String Id, Person person) {

        Optional<Person> p = personService.getPerson(Id);
        if (p.isEmpty()) {
            Person currentPerson = personService.savePerson(person,Id);
            return currentPerson;
        } else {
            return null;
        }
    }

    /**
     * Read - Get one person
     *
     * @param Id Person's FirstName and LastName
     * @return A person object full filled
     */
    @GetMapping("{Id}")
    public Person getPerson(@PathVariable("Id") String Id) {
        Optional<Person> person = personService.getPerson(Id);
        return person.orElse(null);
    }

    /**
     * Update - Update an existing person
     *
     * @param Id     Person's FirstName and LastName
     * @param person - The person object is updated
     * @return person - The person object updated
     */
    @PutMapping("{Id}")
    public Person updatePerson(@PathVariable("Id") String Id, Person person) {
        Optional<Person> p = personService.getPerson(Id);
        if (p.isPresent()) {
            Person currentPerson = p.get();

            String firstname = person.getFirstName();
            if (firstname != null) {
                currentPerson.setFirstName(firstname);
            }
            String lastname = person.getLastName();
            if (firstname != null) {
                currentPerson.setLastName(lastname);
            }
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
            personService.savePerson(currentPerson,Id);
            return currentPerson;
        } else {
            return null;
        }
    }

    /**
     * Delete - Delete a Person Object
     *
     * @param Id     Person's FirstName and LastName
     * @param person The person object is deleted
     * @return person - The Person is deleted
     */
    @DeleteMapping("{Id}")
    public void deletePerson(@PathVariable("Id") String Id, Person person) {
        personService.getPerson(Id);
        personService.deletePerson(person);
    }





    }