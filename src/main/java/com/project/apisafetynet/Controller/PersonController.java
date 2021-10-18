package com.project.apisafetynet.Controller;

import com.project.apisafetynet.Service.PersonService;
import com.project.apisafetynet.model.DTO.ChildrenAndFamilyMembers;
import com.project.apisafetynet.model.ModelRepository.Person;
import com.project.apisafetynet.model.DTO.PersonInformation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * @param person An object person
     * @return The person object saved
     */
    @PostMapping("/person")
    public ResponseEntity<Person> createPerson(@RequestParam String firstName, String lastName, Person person) {
        String functionPath = CLASSPATH + "createPerson";
        log.info("Request received in " + functionPath);

        Optional<Person> searchPerson = personService.getPerson(firstName, lastName);
        if (searchPerson.isEmpty()) {
            log.info("Request is a success");
            personService.savePerson(person);
            return new ResponseEntity<>(person, HttpStatus.CREATED);
        } else {
            log.error("Fail to save"+firstName+ lastName+" because is already exist");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    /**
     * Method's to Get person
     * @param firstName of Person
     * @param lastName of Person
     * @return Person
     */
    @GetMapping("/person")
    public ResponseEntity<Person> getPerson(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {

        String functionPath = CLASSPATH + "getPerson";
        log.info("Request received in " + functionPath);

        Optional<Person> personFound = personService.getPerson(firstName, lastName);
        if (personFound.isPresent()) {
            log.info("Request is a success");
            Person person = personFound.get();
            return new ResponseEntity<>(person, HttpStatus.OK);
        } else {
            log.error("Fail to get Person because "+ firstName+ " "+ lastName+" doesn't exist in DB");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Update - Update an existing person
     *
     * @param firstName of Person
     * @param lastName  of Person
     * @param person    - The person object is updated
     * @return person - The person object updated
     */
    @PutMapping("/person")
    public ResponseEntity<Person> updatePerson(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, Person person) {

        String functionPath = CLASSPATH + "updatePerson";
        log.info("Request received in " + functionPath);

        Optional<Person> p = personService.updatePerson(firstName, lastName, person);
        if (p.isPresent()){
            Person personToUpdate = p.get();
            log.info(" Request is a success" + p + " is updated");
            return new ResponseEntity<>(personToUpdate, HttpStatus.OK);
        } else {

            log.error("Fail to update Person because "+ firstName+" "+ lastName+ " doesn't exist in DB");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    /**
     * Delete - Delete a Person Object
     *
     * @param firstName of Person
     * @param lastName  of Person
     * @return person - The Person is deleted
     */
    @DeleteMapping("/person")
    public ResponseEntity<Person> deletePerson(@RequestParam String firstName, @RequestParam String lastName) {

        String functionPath = CLASSPATH + "deletePerson";
        log.info("Request received in " + functionPath);
       Optional<Person> personToDelete = personService.getPerson(firstName, lastName);
        if(personService.getPerson(firstName, lastName).isPresent()) {
            log.info("Request is success !"+ firstName+ " "+ lastName+ " is delete");
            Person personToDeleteFinally = personToDelete.get();
            personService.deletePerson(personToDeleteFinally);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            log.error("Fail to Delete "+ firstName+ " " + lastName);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    /**
     * get Person information by firstName and lastName
     *
     * @param firstName
     * @param lastName
     * @return Person's information
     */
    @GetMapping("/personInfo")
    public ResponseEntity<ArrayList<PersonInformation>> getPersonInformationList(@RequestParam String firstName, @RequestParam String lastName) {

        String functionPath = CLASSPATH + "getPersonInformationList";

        log.info("Request received in " + functionPath);
        ArrayList<PersonInformation> personInformation = personService.getPersonInformation(firstName, lastName);
        if (!personInformation.isEmpty()){
            log.info("Request is success");
            return new ResponseEntity<>(personInformation, HttpStatus.OK);
        }
        else
        {
            log.error("Fail to get Person Information");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * get list of children and member's family
     *
     * @param address
     * @return a list with child and his family's member
     */
    @GetMapping("/childAlert")
    public ResponseEntity<ChildrenAndFamilyMembers> getChildAndFamilyMembers(@RequestParam String address) {

        String functionPath = CLASSPATH + "getChildAndFamilyMembers";
        log.info("Request received in " + functionPath);
        Optional<ChildrenAndFamilyMembers> ChildrenAndFamilyMembersFound = personService.getListOfChildrenAndFamilyMembersByAddress(address);
        if (!ChildrenAndFamilyMembersFound.isEmpty()) {
            log.info("request is success");
            ChildrenAndFamilyMembers ChildrenAndFamilyMembers = ChildrenAndFamilyMembersFound.get();
            return new ResponseEntity<>(ChildrenAndFamilyMembers, HttpStatus.OK);
        } else {
            log.error("Fail to get Child And His Family Members with his address :" + address);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get Person's Email by City
     *
     * @param city City of Person Object
     * @return A Email's list
     */
    @GetMapping("/communityEmail")
    public ResponseEntity<ArrayList<String>> getEmailByCity(@RequestParam String city) {

        String functionPath = CLASSPATH + "getEmailByCity";
        log.info("Request received in " + functionPath);
        ArrayList<String> listCity;
        listCity = personService.getEmailPersonByCity(city);
        if (!listCity.isEmpty()) {
            log.info("Request is success");
            return new ResponseEntity<>(listCity, HttpStatus.OK);
        } else {
            log.error("Fail to get Email by City:" + city);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}