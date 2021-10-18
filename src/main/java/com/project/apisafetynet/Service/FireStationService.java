package com.project.apisafetynet.Service;

import com.project.apisafetynet.model.DTO.Flood;
import com.project.apisafetynet.model.DTO.InfoByZone;
import com.project.apisafetynet.model.DTO.PersonsAndFireStationWhoDeservedThem;
import com.project.apisafetynet.model.DTO.PhoneAlert;
import com.project.apisafetynet.model.ModelRepository.FireStation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface FireStationService{

    void saveFireStations(List<FireStation> FireStationList);

    Optional<FireStation> saveFireStation(FireStation fireStation);

    Iterable<FireStation> getFireStations();

    Optional<FireStation> getFireStation(long id);

    void deleteFireStation(Long id);

    Optional<InfoByZone> getListPersonInformationByFireStation(String station);

    ArrayList<PhoneAlert> getPhoneNumberByStation(String station);

    ArrayList<PersonsAndFireStationWhoDeservedThem> getPersonListAndStationNumber(String address);

    ArrayList<Flood> getListOfFloodsByStations (ArrayList<String> stations);

    Optional<FireStation> updateFireStation(Long id);
}