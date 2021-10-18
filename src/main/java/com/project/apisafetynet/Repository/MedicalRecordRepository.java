package com.project.apisafetynet.Repository;

import com.project.apisafetynet.model.ModelRepository.MedicalRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends CrudRepository<MedicalRecord,Long> {

   Optional<MedicalRecord> findAllByFirstNameAndLastName(String firstname, String lastname);

   List<MedicalRecord> findMedicalRecordByFirstNameAndLastName(String firstName, String lastName);




}

