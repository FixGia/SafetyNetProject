package com.project.apisafetynet.Repository;


import com.project.apisafetynet.model.ModelRepository.FireStation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Optional;


@Repository
public interface FireStationRepository extends CrudRepository<FireStation, Long> {

   ArrayList<FireStation> findFireStationByStation(String station);
   Optional<FireStation> findFireStationByAddress(String address);


}
