package com.project.apisafetynet.Repository;


import com.project.apisafetynet.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PersonRepository extends JpaRepository<Person,String> {

}
