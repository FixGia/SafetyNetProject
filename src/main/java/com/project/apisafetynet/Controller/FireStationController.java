package com.project.apisafetynet.Controller;
import com.project.apisafetynet.Service.FireStationService;
import com.project.apisafetynet.Service.PersonService;
import com.project.apisafetynet.model.DTO.Flood;
import com.project.apisafetynet.model.DTO.InfoByZone;
import com.project.apisafetynet.model.ModelRepository.FireStation;
import com.project.apisafetynet.model.DTO.PersonsAndFireStationWhoDeservedThem;
import com.project.apisafetynet.model.DTO.PhoneAlert;
import com.project.apisafetynet.model.DTO.FirestationRequest;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@Log4j2
public class FireStationController {

    private final FireStationService fireStationService;
    private final PersonService personService;
    private static final String CLASSPATH = "com.project.ApiSafetyNet.FireStationController";

    public FireStationController(FireStationService fireStationService, PersonService personService) {
        this.fireStationService = fireStationService;
        this.personService = personService;
    }

    @GetMapping("/firestations")
    @ApiOperation(" get every firestations ")
    public Iterable<FireStation> getFirestations() {

        String functionPath = CLASSPATH + "getFireStations";
        log.info("Request received in " + functionPath);
        return fireStationService.getFireStations();
    }

    /**
     * Create a new fireStation
     *
     * @param firestationRequest An object fireStation
     * @return create new fireStation
     */
    @ApiOperation(" create a new firestation ")
    @PostMapping("/firestation")
    public ResponseEntity<FireStation> createFireStation(@RequestBody FirestationRequest firestationRequest) {

        String functionPath = CLASSPATH + "createFireStation";
        log.info("Request received in " + functionPath);

        try {
            FireStation createFireStation = fireStationService.createFireStation(firestationRequest);
            log.info("Request is a success, FireStation with address: " + firestationRequest.getAddress() + " is created");
            return new ResponseEntity<>(createFireStation, HttpStatus.CREATED);
        } catch (Exception exception) {
            log.error("Fail to create FireStation with address" + firestationRequest.getAddress() + " because is already exist");
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}

    /**
     * Delete FireStation by id
     *
     * @param id of object fireStation
     */
    @ApiOperation(" delete firestation")
    @DeleteMapping("/firestation")
    public ResponseEntity<FireStation> deleteFireStation(@RequestParam("Id") Long id) {
            String functionPath = CLASSPATH + "deleteFireStation";
            log.info("Request received in " + functionPath);

        Optional<FireStation> fireStation = fireStationService.getFireStation(id);
        if (fireStation.isPresent()) {
           fireStationService.deleteFireStation(id);
            log.info("Request is a success, FireStation with Id : "+id+" is deleted");
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
            log.error("Fail to delete FireStation with Id "+id+ " because it doesn't exist in Db");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    /**
     * Update FireStation
     *
     * @param id of object fireStation
     * @return an update FireStation
     */
    @ApiOperation(" return firestation updated")
    @PutMapping("/firestation")
    public ResponseEntity<FireStation> updateFireStation(@RequestParam("Id") Long id, @RequestBody FirestationRequest fireStation) {

        String functionPath = CLASSPATH + "updateFireStation";
        log.info("Request received in " + functionPath);

            Optional<FireStation> f = fireStationService.updateFireStation(id, fireStation);
            if (f.isPresent()) {
                FireStation currentFireStation = f.get();
                log.info("Request is a success, FireStation with Id "+id+" is updated");
                return new ResponseEntity<>(currentFireStation, HttpStatus.OK);
            }
                log.error("Fail to Update FireStation  with Id  "+id+" because it doesn't exist in DB");
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

         }

    /**
     * @param station number's station
     * @return Person Information By number Station
     */
    @ApiOperation("return every person's information by station number")
    @GetMapping("/firestation")
    public ResponseEntity<Optional<InfoByZone>> getPersonInfoByFireStation(@RequestParam("station") String station) {
        String functionPath = CLASSPATH + "getPersonInfoByFireStation";
            log.info("Request received in " + functionPath);

            Optional<InfoByZone> PersonInfoByFireStationList =  fireStationService.getListPersonInformationByFireStation(station);
            if (PersonInfoByFireStationList.isPresent()){
                log.info( "Request is a success, Person Information By FireStation : "+ PersonInfoByFireStationList);
                return new ResponseEntity<>(PersonInfoByFireStationList, HttpStatus.OK);

        }
                log.error("Fail to get Person Information by FireStation number :"+station+ "because it doesn't exist in Db");
                return new ResponseEntity<>(Optional.empty(), HttpStatus.NOT_FOUND);
    }

    /**
     * @param station
     * @return
     */
    @ApiOperation("return every phone number by station number")
    @GetMapping("/phoneNumber")
    public ResponseEntity<ArrayList<PhoneAlert>> getPhoneNumber(@RequestParam("station") String station) {
            String functionPath = CLASSPATH + "getPhoneNumber";
            log.info("Request received in " + functionPath);
            ArrayList<PhoneAlert> phoneNumberByStation = fireStationService.getPhoneNumberByStation(station);
            if (!phoneNumberByStation.isEmpty()) {
                log.info("Request is a success phone Number list : "+phoneNumberByStation);
                return new ResponseEntity<>(phoneNumberByStation, HttpStatus.OK);
        }
            log.error("Fail to get PhoneNumber by FireStation number:" + station);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    /**
     * @param address
     * @return
     */
    @ApiOperation(" return a list of person deserved by station")
    @GetMapping("/fire")
    public ResponseEntity<ArrayList<PersonsAndFireStationWhoDeservedThem>> getListPersonDeservedByStation(@RequestParam("address") String address) {
        String functionPath = CLASSPATH + "getListPersonDeservedByStation";
        log.info("Request received in " + functionPath);
        ArrayList<PersonsAndFireStationWhoDeservedThem> ListPersonDeservedByStation = fireStationService.getPersonListAndStationNumber(address);
        if (!ListPersonDeservedByStation.isEmpty()) {
            log.info("Request is a success, List Of Person Deserved By Station: " + ListPersonDeservedByStation);
            return new ResponseEntity<>(ListPersonDeservedByStation, HttpStatus.OK);
        }
            log.error("Fail to get List of Person Deserved by FireStation address :" + address);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    /**
     * @param stations
     * @return
     */

    @ApiOperation(" return every flood by stations")
    @GetMapping("/flood/stations")
    public ResponseEntity<ArrayList<Flood>> getFloodByStations(@RequestParam("stations") ArrayList<String> stations) {
        String functionPath = CLASSPATH + "getFloodByStations";
        log.info("Request received in " + functionPath);

        ArrayList<Flood> FloodByStationsList = fireStationService.getListOfFloodsByStations(stations);
        if (!FloodByStationsList.isEmpty()) {
            log.info("Request is a success, Flood By Station : " + FloodByStationsList);
            return new ResponseEntity<>(FloodByStationsList, HttpStatus.OK);
        }
            log.error("Fail to get a list of Person Deserved by List of FireStations : " + stations);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}

