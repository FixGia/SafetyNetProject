package com.project.apisafetynet.Repository;


import com.project.apisafetynet.model.Firestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FireStationRepository extends JpaRepository <Firestation, Long> {

}
