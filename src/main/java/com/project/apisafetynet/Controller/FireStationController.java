package com.project.apisafetynet.Controller;
import com.project.apisafetynet.Service.FireStationService;
import com.project.apisafetynet.model.FireStation;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("firestations")
public class FireStationController {

    final FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @GetMapping()
    public Iterable<FireStation> getFirestations() {
        return fireStationService.getFireStations();
    }
    /**
     * Create a new fireStation
     *
     * @param fireStation An object fireStation
     *
     * @return create new fireStation
     */
    @PostMapping()
    public FireStation createFireStation(FireStation fireStation) {
        return fireStationService.saveFireStation(fireStation);
    }

    /**
     * Get fireStation by id
     *
     * @param Id of object fireStation
     *
     * @return get fireStation
     */
    @GetMapping("{Id}")
    public Optional<FireStation> getFireStation(@PathVariable("Id") long Id) {
        return fireStationService.getFireStation(Id);
    }

    /**
     * Delete FireStation by id
     *
     * @param id of object fireStation
     *
     */
    @DeleteMapping("{id}")
    public void deleteFireStation(@PathVariable("id") Long id) {
        fireStationService.deleteFireStation(id);
    }

    /**
     * Update FireStation
     * @param id of object fireStation
     * @return an update FireStation
     */
    @PutMapping("{id}")
    public FireStation updateFireStation(@PathVariable("id") Long id) {
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


