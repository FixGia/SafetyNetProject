package com.project.apisafetynet.Service;


import com.project.apisafetynet.Repository.PersonRepository;
import com.project.apisafetynet.model.Person;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Data
public class PersonServiceImpl implements PersonService {

    final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void SavePersons(List<Person> personsList) {
        this.personRepository.saveAll(personsList);
    }

    @Override
    public Iterable<Person> getPersons() {
        return personRepository.findAll();
    }

    @Override
    public Person deletePerson(Person person) {
        this.personRepository.delete(person);
        return person;
    }

    @Override
    public Person savePerson(Person person) {
        this.personRepository.save(person);
        return person;
    }

    @Override
    public Optional<Person> getPersonByFirstNameAndLastName(Person firstname, Person lastname) {
        return personRepository.findPersonByFirstNameAndLastName(firstname,lastname);

    }



}