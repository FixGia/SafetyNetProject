package com.project.apisafetynet.Repository;


import com.project.apisafetynet.model.Persons;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Persons,Long> {
}
