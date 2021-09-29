package com.project.apisafetynet.Controller;
import com.project.apisafetynet.Service.FireStationService;
import com.project.apisafetynet.Service.PersonService;
import com.project.apisafetynet.model.InfoByZone;
import com.project.apisafetynet.model.PersonsAndFireStationWhoDeservedThem;
import com.project.apisafetynet.model.PhoneAlert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;

@RestController
public class SpecificEndPointsController {


    final PersonService personService;
    final FireStationService fireStationService;

    public SpecificEndPointsController(PersonService personService, FireStationService fireStationService) {
        this.personService = personService;
        this.fireStationService = fireStationService;
    }

    /**
     * Get Person's Email by City
     * @param city City of Person Object
     * @return A Email's list
     */
    @GetMapping("/communityEmail")
    public ArrayList<String> getEmailByCity(@RequestParam String city) {
        ArrayList<String> listCity;
        listCity = personService.getEmailPersonByCity(city);
        return listCity;
    }
    @GetMapping("/fireStation")
    public Optional<InfoByZone>getPersonInfoByFireStation(@RequestParam String station) {
       return fireStationService.getListPersonInformationByFireStation(station);

    }

    @GetMapping("/phoneNumber")
    public ArrayList<PhoneAlert> getPhoneNumber(@RequestParam String station) {
        return fireStationService.getPhoneNumberByStation(station);
    }

    @GetMapping("/fire")
    public ArrayList<PersonsAndFireStationWhoDeservedThem> getListPersonDeservedByStation(@RequestParam String address) {
        return fireStationService.getPersonListAndStationNumber(address);
    }


}
