package com.project.apisafetynet.Repository;


import com.project.apisafetynet.model.FireStation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FireStationRepository extends CrudRepository<FireStation, Long> {

}
