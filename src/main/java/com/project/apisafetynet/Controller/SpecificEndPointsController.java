package com.project.apisafetynet.Controller;
import com.project.apisafetynet.Service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class SpecificEndPointsController {


    final PersonService personService;

    public SpecificEndPointsController(PersonService personService) {
        this.personService = personService;
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




}
