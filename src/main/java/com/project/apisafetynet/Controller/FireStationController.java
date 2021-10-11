package com.project.apisafetynet.Controller;
import com.project.apisafetynet.Service.FireStationService;
import com.project.apisafetynet.Service.PersonService;
import com.project.apisafetynet.model.DTO.Flood;
import com.project.apisafetynet.model.DTO.InfoByZone;
import com.project.apisafetynet.model.ModelRepository.FireStation;
import com.project.apisafetynet.model.DTO.PersonsAndFireStationWhoDeservedThem;
import com.project.apisafetynet.model.DTO.PhoneAlert;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@Log4j2
public class FireStationController {

    final FireStationService fireStationService;
    final PersonService personService;
    private static final String CLASSPATH = "com.project.ApiSafetyNet.FireStationController";

    public FireStationController(FireStationService fireStationService, PersonService personService) {
        this.fireStationService = fireStationService;
        this.personService = personService;
    }

    @GetMapping("/firestations")
    public Iterable<FireStation> getFirestations() {

        String functionPath = CLASSPATH + "getFireStations";
        log.info("Request received in " + functionPath);

        return fireStationService.getFireStations();

    }
    /**
     * Create a new fireStation
     *
     * @param fireStation An object fireStation
     *
     * @return create new fireStation
     */
    @PostMapping("/firestation")
    public FireStation createFireStation(FireStation fireStation) {



        try {
            fireStationService.saveFireStation(fireStation);
            String functionPath = CLASSPATH + "createFireStation";
            log.info("Request received in " + functionPath);
        } catch (Exception exception) {
            exception.printStackTrace();
            log.error("Exception received to createFireStation");
        }

        return null;
    }

    /**
     * Delete FireStation by id
     *
     * @param id of object fireStation
     *
     */
    @DeleteMapping("/firestation")
    public void deleteFireStation(@RequestParam("Id") Long id) {

        String functionPath = CLASSPATH + "deleteFireStation";
        log.info("Request received in " + functionPath);

        fireStationService.deleteFireStation(id);
    }

    /**
     * Update FireStation
     * @param id of object fireStation
     * @return an update FireStation
     */
    @PutMapping("/firestation")
    public FireStation updateFireStation(@RequestParam("Id") Long id) {

        try {
            String functionPath = CLASSPATH + "updateFireStation";
            log.info("Request received in " + functionPath);

            Optional<FireStation> f = fireStationService.getFireStation(id);
            if (f.isPresent()) {
                FireStation currentFireStation = f.get();

                String address = currentFireStation.getAddress();
                if (address != null) {
                    currentFireStation.setAddress(address);
                }
                String station = currentFireStation.getStation();
                if (station != null) {
                    currentFireStation.setStation(station);
                }
                fireStationService.saveFireStation(currentFireStation);
                return currentFireStation;
            } else {
                return null;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

        /**
     *
     * @param station
     * @return
     */
    @GetMapping("/firestation")
    public Optional<InfoByZone>getPersonInfoByFireStation(@RequestParam("station") String station) {

        String functionPath = CLASSPATH + "getPersonInfoByFireStation";
        log.info("Request received in " + functionPath);

        return fireStationService.getListPersonInformationByFireStation(station);

    }

    /**
     *
     * @param station
     * @return
     */
    @GetMapping("/phoneNumber")
    public ArrayList<PhoneAlert> getPhoneNumber(@RequestParam("station") String station) {

       String functionPath = CLASSPATH + "getPhoneNumber";
        log.info("Request received in " + functionPath);

        return fireStationService.getPhoneNumberByStation(station);
    }

    /**
     *
     * @param address
     * @return
     */
    @GetMapping("/fire")
    public ArrayList<PersonsAndFireStationWhoDeservedThem> getListPersonDeservedByStation(@RequestParam("address") String address) {

        String functionPath = CLASSPATH + "getListPersonDeservedByStation";
        log.info("Request received in " + functionPath);

        return fireStationService.getPersonListAndStationNumber(address);
    }

    /**
     *
     * @param stations
     * @return
     */

    @GetMapping("/flood/stations")
    public ArrayList<Flood> getFloodByStations(@RequestParam("stations") ArrayList<String> stations) {

        String functionPath = CLASSPATH + "getFloodByStations";
        log.info("Request received in " + functionPath);

        return fireStationService.getListOfFloodsByStations(stations);
    }

    }



