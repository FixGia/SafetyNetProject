package com.project.apisafetynet.Service;

import com.project.apisafetynet.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface FireStationService{

    void saveFireStations(List<FireStation> FireStationList);

    FireStation saveFireStation(FireStation fireStation);

    Iterable<FireStation> getFireStations();

    Optional<FireStation> getFireStation(long id);

    void deleteFireStation(long id);

    ArrayList<FireStation> getFireStationByStation(String station);

    Optional<InfoByZone> getListPersonInformationByFireStation(String station);

    ArrayList<PhoneAlert> getPhoneNumberByStation(String station);

    ArrayList<PersonsAndFireStationWhoDeservedThem> getPersonListAndStationNumber(String address);

    ArrayList<Flood> getListOfFloodsByStations (ArrayList<String> stations);
}