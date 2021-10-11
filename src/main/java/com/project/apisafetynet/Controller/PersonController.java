package com.project.apisafetynet.Controller;

import com.project.apisafetynet.Service.PersonService;
import com.project.apisafetynet.model.DTO.ChildrenAndFamilyMembers;
import com.project.apisafetynet.model.ModelRepository.Person;
import com.project.apisafetynet.model.DTO.PersonInformation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController

@Log4j2
public class PersonController {

    final PersonService personService;
    private static final String CLASSPATH = "com.project.ApiSafetyNet.PersonController";
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Create - Add a new person
     *
     * @param person An object person
     * @return The person object saved
     */
    @PostMapping("/person")
    public Person createPerson(@RequestParam String firstName, String lastName, Person person) {
        String functionPath = CLASSPATH + "createPerson";
        log.info("Request received in " + functionPath);

        try {
           Optional<Person> searchPerson =  personService.getPerson(firstName, lastName);
            if (searchPerson.isEmpty()) {
                personService.savePerson(person);
                return person;
        }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
       return null;
    }
        @GetMapping("/person")
    public Person getPerson( @RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName) {

            String functionPath = CLASSPATH + "getPerson";
            log.info("Request received in " + functionPath);

        Optional<Person> person = personService.getPerson(firstName, lastName);
        return person.orElse(null);
    }

    /**
     * Update - Update an existing person
     *
     * @param firstName of Person
     * @param lastName of Person
     * @param person - The person object is updated
     * @return person - The person object updated
     */
    @PutMapping("/person")
    public Person updatePerson(@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName,  Person person) {

        String functionPath = CLASSPATH + "updatePerson";
        log.info("Request received in " + functionPath);

        Optional<Person> p = personService.getPerson(firstName, lastName);
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
            personService.savePerson(currentPerson);
            return currentPerson;
        } else {
            return null;
        }
    }

    /**
     * Delete - Delete a Person Object
     *
     * @param firstName of Person
     * @param lastName of Person
     * @param person The person object is deleted
     * @return person - The Person is deleted
     */
    @DeleteMapping("/person")
    public void deletePerson( Person person, @RequestParam String firstName,@RequestParam String lastName) {

        String functionPath = CLASSPATH + "deletePerson";
        log.info("Request received in " + functionPath);

        personService.getPerson(firstName, lastName);
        personService.deletePerson(person);
    }

    /**
     * get Person information by firstName and lastName
     * @param firstName
     * @param lastName
     * @return Person's information
     */
    @GetMapping("/personInfo")
    public ArrayList<PersonInformation> getPersonInformationList(@RequestParam String firstName, @RequestParam String lastName){

        String functionPath = CLASSPATH + "getPersonInformationList";
        log.info("Request received in " + functionPath);

        return personService.getPersonInformation(firstName, lastName);
    }

    /**
     * get list of children and member's family
     * @param address
     * @return a list with child and his family's member
     */
    @GetMapping("/childAlert")
    public Optional<ChildrenAndFamilyMembers> getChildAndFamilyMembers(@RequestParam String address){

        String functionPath = CLASSPATH + "getChildAndFamilyMembers";
        log.info("Request received in " + functionPath);

        return personService.getListOfChildrenAndFamilyMembersByAddress(address);
    }

    /**
     * Get Person's Email by City
     * @param city City of Person Object
     * @return A Email's list
     */
    @GetMapping("/communityEmail")
    public ArrayList<String> getEmailByCity(@RequestParam String city) {

        String functionPath = CLASSPATH + "getEmailByCity";
        log.info("Request received in " + functionPath);

        ArrayList<String> listCity;
        listCity = personService.getEmailPersonByCity(city);

        return listCity;
    }



}