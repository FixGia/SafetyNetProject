package com.project.apisafetynet.Repository;


import com.project.apisafetynet.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;



@Repository
public interface PersonRepository extends CrudRepository<Person,String> {

    ArrayList<Person> findPersonByCity(String city);

    ArrayList<Person> findPersonByAddress(String address);


}
