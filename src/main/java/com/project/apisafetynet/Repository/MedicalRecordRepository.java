package com.project.apisafetynet.Repository;

import com.project.apisafetynet.model.MedicalRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordRepository extends CrudRepository<MedicalRecord,String> {
}
