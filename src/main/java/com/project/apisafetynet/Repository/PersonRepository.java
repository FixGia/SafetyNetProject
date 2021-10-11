package com.project.apisafetynet.Repository;


import com.project.apisafetynet.model.ModelRepository.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Optional;


@Repository
public interface PersonRepository extends CrudRepository<Person,Long> {

    ArrayList<Person> findPersonByCity(String city);

    ArrayList<Person> findPersonByAddress(String address);

    ArrayList<Person> findPersonByFirstNameAndLastName(String firstName, String lastName);

    Optional<Person> findByFirstNameAndLastName(String firstName, String lastName);
}
