package com.project.apisafetynet.Service;


import com.project.apisafetynet.Repository.MedicalRecordRepository;
import com.project.apisafetynet.Repository.PersonRepository;
import com.project.apisafetynet.model.DTO.*;
import com.project.apisafetynet.model.ModelRepository.MedicalRecord;
import com.project.apisafetynet.model.ModelRepository.Person;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Data
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
            Optional<Person> personToCreate = personRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
            if (personToCreate.isPresent()){
                return Optional.empty();
            }
            personRepository.save(person);
        } catch (Exception e) {
            System.out.println("Error attempting to add a new person in [PersonServiceImpl/addNewPerson] method");
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
            Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findAllByFirstnameAndLastname(person.getFirstName(), person.getLastName());
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

    public ChildrenAndFamilyMembers buildChildrenAndFamilyMembers(@NotNull ArrayList<Person> personAtThisAddress) {
        ArrayList<Child> childrenArrayList = new ArrayList<>();
        ArrayList<FamilyMembers> familyMembersList = new ArrayList<>();
        for (Person person : personAtThisAddress) {
            Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findAllByFirstnameAndLastname(person.getFirstName(), person.getLastName());
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

