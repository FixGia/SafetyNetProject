package com.project.apisafetynet.Repository;

import com.project.apisafetynet.model.ModelRepository.Medications;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationsRepository extends CrudRepository<Medications,String> {

}
