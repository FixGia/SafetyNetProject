package com.project.apisafetynet.Repository;


import com.project.apisafetynet.model.Firestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FireStationRepository extends CrudRepository<Firestation, Long> {

}
