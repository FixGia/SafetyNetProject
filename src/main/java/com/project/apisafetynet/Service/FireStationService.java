package com.project.apisafetynet.Service;

import com.project.apisafetynet.model.FireStation;

import java.util.List;
import java.util.Optional;

public interface FireStationService{

    void saveFireStations(List<FireStation> FireStationList);

    FireStation saveFireStation(FireStation fireStation);

    Optional<FireStation> getFireStation(long id);

    void deleteFireStation(long id);

}
