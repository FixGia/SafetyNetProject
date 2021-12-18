package com.project.apisafetynet.Service;


import com.project.apisafetynet.Repository.MedicalRecordRepository;
import com.project.apisafetynet.Repository.PersonRepository;
import com.project.apisafetynet.model.DTO.*;
import com.project.apisafetynet.model.DTO.PersonRequest;
import com.project.apisafetynet.model.ModelRepository.MedicalRecord;
import com.project.apisafetynet.model.ModelRepository.Person;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Data
@Slf4j
public class PersonServiceImpl implements PersonService {

    final PersonRepository personRepository;
    final MedicalRecordRepository medicalRecordRepository;
   final CalculateAgeService calculateAgeService;

    public PersonServiceImpl(PersonRepository personRepository, MedicalRecordRepository medicalRecordRepository, CalculateAgeService calculateAgeService) {
        this.personRepository = personRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.calculateAgeService = calculateAgeService;
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
    public Optional<Person> savePerson(Person person) {
        try {
            personRepository.save(person);
        } catch (Exception e) {
            log.debug("Error attempting to add a new person in [PersonServiceImpl/savePerson] method");
        }
        return Optional.of(person);
    }


    @Override
    public Optional<Person> getPerson(String firstName, String lastName) {
        return personRepository.findByFirstNameAndLastName(firstName, lastName);

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
    public ArrayList<PersonInformation> getPersonInformation(String firstName, String lastName) {
        ArrayList<Person> personWithSpecificName = personRepository.findPersonByFirstNameAndLastName(firstName, lastName);
        ArrayList<PersonInformation> personInformationList = new ArrayList<>();
        if (personWithSpecificName.isEmpty()) {
            return personInformationList;
        }
        for(Person person : personWithSpecificName) {
            Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findAllByFirstNameAndLastName(person.getFirstName(), person.getLastName());
            Age CalculateAge = new Age(medicalRecord.get().getBirthdate(), "MM/dd/yyyy");
            int age = calculateAgeService.CalculateAge(CalculateAge);
            PersonInformation personInformation = new PersonInformation();
            personInformation.setFirstName(person.getFirstName());
            personInformation.setLastName(person.getLastName());
            personInformation.setAddress(person.getAddress());
            personInformation.setAge(age);
            personInformation.setEmail(person.getEmail());
            personInformation.setAllergies(medicalRecord.get().getAllergies());
            personInformation.setMedications(medicalRecord.get().getMedications());
            personInformationList.add(personInformation);
        }
        return personInformationList;
    }


    public Optional<ChildrenAndFamilyMembers> getListOfChildrenAndFamilyMembersByAddress(String address) {
        ArrayList<Person> personAtThisAddress = personRepository.findPersonByAddress(address);
        if (personAtThisAddress.isEmpty()) {
            return Optional.empty();
        }
        ChildrenAndFamilyMembers childrenAndFamilyMembers = buildChildrenAndFamilyMembers(personAtThisAddress);
        if (childrenAndFamilyMembers.getChildrenArrayList().isEmpty()) {
            return Optional.of(new ChildrenAndFamilyMembers(new ArrayList<>(), new ArrayList<>()));
        }
        return Optional.of(childrenAndFamilyMembers);
    }

    @Override
    public Person updatePerson(String firstName, String lastName, PersonRequest person) {

        Optional<Person> p = personRepository.findByFirstNameAndLastName(firstName, lastName);

        if (p.isPresent()) {
            Person currentPerson = p.get();

            String firstname = person.getFirstName();
            if (firstname != null) {
                currentPerson.setFirstName(firstname);
            }
            String lastname = person.getLastName();
            if (lastname != null) {
                currentPerson.setLastName(lastname);
            }
            String email = person.getEmail();
            if (email != null) {
                currentPerson.setEmail(email);
            }
            String address = person.getAddress();
            if (address != null) {
                currentPerson.setAddress(address);
            }
            String city = person.getCity();
            if (city != null) {
                currentPerson.setCity(city);
            }
            String zip = person.getZip();
            if (zip != null) {
                currentPerson.setZip(zip);
            }
            String phone = person.getPhone();
            if (phone != null) {
                currentPerson.setPhone(phone);
            }
            personRepository.save(currentPerson);
            return currentPerson;
        }
        return p.get();
    }

    @Override
    public Person createPerson(PersonRequest personRequest) {

        Optional<Person> searchPerson = personRepository.findByFirstNameAndLastName(personRequest.getFirstName(), personRequest.getLastName());
        if (!searchPerson.isPresent()) {
            Person createPerson = new Person();
            createPerson.setFirstName(personRequest.getFirstName());
            createPerson.setLastName(personRequest.getLastName());
            createPerson.setAddress(personRequest.getAddress());
            createPerson.setCity(personRequest.getCity());
            createPerson.setPhone(personRequest.getPhone());
            createPerson.setZip(personRequest.getZip());
            createPerson.setEmail(personRequest.getEmail());
            personRepository.save(createPerson);
            return createPerson;
        }
        log.error("We can't create a new Person because {} is already exist", searchPerson);
        return null;
    }

    public ChildrenAndFamilyMembers buildChildrenAndFamilyMembers(@NotNull ArrayList<Person> personAtThisAddress) {
        ArrayList<Child> childrenArrayList = new ArrayList<>();
        ArrayList<FamilyMembers> familyMembersList = new ArrayList<>();
        for (Person person : personAtThisAddress) {
            Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findAllByFirstNameAndLastName(person.getFirstName(), person.getLastName());
            int age = -1;
            if (medicalRecord.isPresent()) {
                Age CalculateAge= new Age(medicalRecord.get().getBirthdate(), "MM/dd/yyyy");
                age = calculateAgeService.CalculateAge(CalculateAge);
            }
            if (age < 19 && age != -1) {
                childrenArrayList.add(new Child(person.getFirstName(), person.getLastName(), age));
            } else {
                familyMembersList.add(new FamilyMembers(person.getFirstName(), person.getLastName()));

            }
        }
        return new ChildrenAndFamilyMembers(childrenArrayList, familyMembersList);
    }
}

