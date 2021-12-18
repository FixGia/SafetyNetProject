package com.project.apisafetynet.Controller;

import com.project.apisafetynet.Service.PersonService;
import com.project.apisafetynet.model.DTO.ChildrenAndFamilyMembers;
import com.project.apisafetynet.model.ModelRepository.Person;
import com.project.apisafetynet.model.DTO.PersonRequest;
import com.project.apisafetynet.model.DTO.PersonInformation;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("create person")
    @PostMapping("/person")
    public ResponseEntity<Person> createPerson(@RequestBody PersonRequest person) {
        String functionPath = CLASSPATH + "createPerson";
        log.info("Request received in " + functionPath);

        try{
            log.info("Request is a success");
           Person createPerson = personService.createPerson(person);
            return new ResponseEntity<>(createPerson, HttpStatus.CREATED);
        } catch (Exception e){
            log.error("Fail to save {}", person);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    /**
     * Method's to Get person
     * @param firstName of Person
     * @param lastName of Person
     * @return Person
     */
    @ApiOperation(" get a person by firstname and lastname")
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
    @ApiOperation(" update a person by firstname and lastname")
    @PutMapping("/person")
    public ResponseEntity<Person> updatePerson(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,@RequestBody PersonRequest person) {

        String functionPath = CLASSPATH + "updatePerson";
        log.info("Request received in " + functionPath);

        try { Person updatePerson = personService.updatePerson(firstName, lastName, person);
            log.info(" Request is a success {} is updated", updatePerson.getFirstName(), updatePerson.getLastName());
            return new ResponseEntity<>(updatePerson, HttpStatus.OK);
        } catch (Exception e){
            log.error("Fail to update Person {}", firstName, lastName);
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
    @ApiOperation(" delete a person with firstname and lastname")
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
        }
            log.error("Fail to Delete "+ firstName+ " " + lastName);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }
    /**
     * get Person information by firstName and lastName
     *
     * @param firstName person's firstname
     * @param lastName person's lastname
     * @return Person's information
     */
    @GetMapping("/personInfo")
    @ApiOperation(" get every information about a  with firstname and lastname")
    public ResponseEntity<ArrayList<PersonInformation>> getPersonInformationList(@RequestParam String firstName, @RequestParam String lastName) {

        String functionPath = CLASSPATH + "getPersonInformationList";

        log.info("Request received in " + functionPath);
        ArrayList<PersonInformation> personInformation = personService.getPersonInformation(firstName, lastName);
        if (!personInformation.isEmpty()){
            log.info("Request is success");
            return new ResponseEntity<>(personInformation, HttpStatus.OK);
        }

            log.error("Fail to get Person Information");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }

    /**
     * get list of children and member's family
     *
     * @param address
     * @return a list with child and his family's member
     */
    @GetMapping("/childAlert")
    @ApiOperation(" get every child and his family's member with his address")
    public ResponseEntity<ChildrenAndFamilyMembers> getChildAndFamilyMembers(@RequestParam String address) {

        String functionPath = CLASSPATH + "getChildAndFamilyMembers";
        log.info("Request received in " + functionPath);
        Optional<ChildrenAndFamilyMembers> ChildrenAndFamilyMembersFound = personService.getListOfChildrenAndFamilyMembersByAddress(address);
        if (!ChildrenAndFamilyMembersFound.isEmpty()) {
            log.info("request is success");
            ChildrenAndFamilyMembers ChildrenAndFamilyMembers = ChildrenAndFamilyMembersFound.get();
            return new ResponseEntity<>(ChildrenAndFamilyMembers, HttpStatus.OK);
        }
            log.error("Fail to get Child And His Family Members with his address :" + address);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }

    /**
     * Get Person's Email by City
     *
     * @param city City of Person Object
     * @return A Email's list
     */
    @GetMapping("/communityEmail")
    @ApiOperation(" get every person's email who live in this city")
    public ResponseEntity<ArrayList<String>> getEmailByCity(@RequestParam String city) {

        String functionPath = CLASSPATH + "getEmailByCity";
        log.info("Request received in " + functionPath);
        ArrayList<String> listCity;
        listCity = personService.getEmailPersonByCity(city);
        if (!listCity.isEmpty()) {
            log.info("Request is success");
            return new ResponseEntity<>(listCity, HttpStatus.OK);
        }
            log.error("Fail to get Email by City:" + city);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }
}