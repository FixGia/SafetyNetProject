package com.project.apisafetynet.Repository;

import com.project.apisafetynet.model.ModelRepository.MedicalRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends CrudRepository<MedicalRecord,String> {

   Optional<MedicalRecord> findAllByFirstnameAndLastname(String firstname, String lastname);
   ArrayList<MedicalRecord>findMedicalRecordByFirstnameAndLastname(String firstName,String lastName);

}

