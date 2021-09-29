package com.project.apisafetynet.Service;


import com.project.apisafetynet.Repository.PersonRepository;
import com.project.apisafetynet.model.Person;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public void deletePerson(Person person) {
        this.personRepository.delete(person);

    }

    @Override
    public Person savePerson(Person person, String Id) {
        this.personRepository.save(person);
        person.setId(Id);
        return person;
    }

    @Override
    public Optional<Person> getPerson(String Id) {
        return personRepository.findById(Id);

    }

    @Override
    public ArrayList<String> getEmailPersonByCity(String city) {
        ArrayList<Person> personsLiveInThisCity = personRepository.findPersonByCity(city);
        ArrayList<String> emailListOfPersonsLiveInThisCity = new ArrayList<>();
        if (personsLiveInThisCity.isEmpty()) {
            return emailListOfPersonsLiveInThisCity;
        }
        for (Person person : personsLiveInThisCity) {
            emailListOfPersonsLiveInThisCity.add(person.getEmail());
        }
        return emailListOfPersonsLiveInThisCity;

    }

    @Override
    public ArrayList<String> getPersonInformation(String address) {
        ArrayList<Person> personLiveAtThisAddress = personRepository.findPersonByAddress(address);
        ArrayList<String> personInformation = new ArrayList<>();
        if (personLiveAtThisAddress.isEmpty()) {
            return personInformation;
        }
        for(Person person : personLiveAtThisAddress) {
            personInformation.add(person.getFirstName().concat(person.getLastName().concat(person.getAddress().concat(person.getPhone()))));
        }
        return personInformation;
    }

}

