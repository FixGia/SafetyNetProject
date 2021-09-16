package com.project.apisafetynet.Controller;

import com.project.apisafetynet.Service.FireStationService;
import com.project.apisafetynet.model.FireStation;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class FireStationController {

    final FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    /**
     * Create a new fireStation
     *
     * @param fireStation An object fireStation
     *
     * @return create new fireStation
     */
    @PostMapping("/fireStation")
    public FireStation createFireStation(@RequestBody FireStation fireStation) {
        return fireStationService.saveFireStation(fireStation);
    }

    /**
     * Get fireStation by id
     *
     * @param id of object fireStation
     *
     * @return get fireStation
     */
    @GetMapping("/fireStation{id}")
    public Optional<FireStation> getFireStation(@PathVariable("id") long id) {
        return fireStationService.getFireStation(id);
    }

    /**
     * Delete FireStation by id
     *
     * @param id of object fireStation
     *
     */
    @DeleteMapping("/fireStation{id}")
    public void deleteFireStation(@PathVariable("id") long id) {
        fireStationService.deleteFireStation(id);
    }

    /**
     * Update FireStation
     * @param id of object fireStation
     * @return an update FireStation
     */
    @PutMapping("/fireStation{id}")
    public FireStation updateFireStation(@PathVariable("id") long id) {
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
        }
         else {
            return null;
        }

    }
}


