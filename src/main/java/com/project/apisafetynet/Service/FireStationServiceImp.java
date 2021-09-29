package com.project.apisafetynet.Service;
import com.project.apisafetynet.Repository.FireStationRepository;
import com.project.apisafetynet.Repository.MedicalRecordRepository;
import com.project.apisafetynet.Repository.PersonRepository;
import com.project.apisafetynet.model.*;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class FireStationServiceImp implements FireStationService {

    final FireStationRepository fireStationRepository;
    final PersonRepository personRepository;
    final MedicalRecordRepository medicalRecordRepository;
    final CalculateAgeService calculateAgeService;


    public FireStationServiceImp(FireStationRepository fireStationRepository, PersonRepository personRepository, MedicalRecordRepository medicalRecordRepository, CalculateAgeService calculateAgeService) {
        this.fireStationRepository = fireStationRepository;
        this.personRepository = personRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.calculateAgeService = calculateAgeService;

    }


    @Override
    public void saveFireStations(List<FireStation> FireStationList) {
        this.fireStationRepository.saveAll(FireStationList);
    }

    @Override
    public FireStation saveFireStation(FireStation fireStation) {
        return this.fireStationRepository.save(fireStation);
    }

    @Override
    public Iterable<FireStation> getFireStations() {
        return this.fireStationRepository.findAll();
    }

    @Override
    public Optional<FireStation> getFireStation(long id) {
        return this.fireStationRepository.findById(id);
    }

    @Override
    public void deleteFireStation(long id) {
        this.fireStationRepository.deleteById(id);
    }

    @Override
    public ArrayList<FireStation> getFireStationByStation(String station) {
        return fireStationRepository.findFireStationByStation(station);
    }

    //@FIXME Modify Method's to count every MedicalRecord. Not just the first
    public Optional<InfoByZone> getListPersonInformationByFireStation(String station) {
        ArrayList<FireStation> getFireStationNumber = fireStationRepository.findFireStationByStation(station);
        if (getFireStationNumber.isEmpty()) {
            return Optional.empty();
        }
        ArrayList<PersonCoveredByFireStation> personListByStation = new ArrayList<>();
        InfoByZone infoByZone = new InfoByZone();
        for (FireStation fireStation : getFireStationNumber) {
            ArrayList<Person> personArrayList = personRepository.findPersonByAddress(fireStation.getAddress());
            for (Person person : personArrayList) {
                PersonCoveredByFireStation personCoveredByStation = new PersonCoveredByFireStation();
                personCoveredByStation.setFirstName(person.getFirstName());
                personCoveredByStation.setLastName(person.getLastName());
                personCoveredByStation.setAddress(person.getAddress());
                personCoveredByStation.setPhone(person.getPhone());
                personCoveredByStation.setId(person.getId());
                personListByStation.add(personCoveredByStation);
                String firstName = personCoveredByStation.getFirstName();
                String lastname = personCoveredByStation.getLastName();
                Optional<MedicalRecord> medicalRecordsList = medicalRecordRepository.findAllByFirstnameAndLastname(firstName, lastname);
                int adults = 0;
                int child = 0;
                Age CalculateAge = new Age(medicalRecordsList.get().getBirthdate(), "MM/dd/yyyy");
                int age = calculateAgeService.CalculateAge(CalculateAge);
                if (age >= 19) {
                    adults++;
                } else {
                    child++;
                }
                infoByZone.setAdults(adults);
                infoByZone.setChild(child);
                infoByZone.setPersonCoveredByFireStation(personListByStation);
            }

        }
        return Optional.of(infoByZone);
    }

    public ArrayList<PhoneAlert> getPhoneNumberByStation(String station) {

        ArrayList<FireStation> getFireStationNumber = fireStationRepository.findFireStationByStation(station);
        ArrayList<PhoneAlert> personPhoneNumberByStation = new ArrayList<>();
        for (FireStation fireStation : getFireStationNumber) {
            ArrayList<Person> personArrayList = personRepository.findPersonByAddress(fireStation.getAddress());
            for (Person person : personArrayList) {
                PhoneAlert phoneNumberByStation = new PhoneAlert();
                person.getPhone();
                phoneNumberByStation.setPhone(person.getPhone());
                personPhoneNumberByStation.add(phoneNumberByStation);
                System.out.println(personArrayList);
            }
        }
        return personPhoneNumberByStation;
    }

    public ArrayList<PersonsAndFireStationWhoDeservedThem> getPersonListAndStationNumber(String address) {
        ArrayList<Person> personListByAddress = personRepository.findPersonByAddress(address);
        ArrayList<PersonsAndFireStationWhoDeservedThem> personAndFireStationNumberWhoServedHimArrayList = new ArrayList<>();
        if (personListByAddress.isEmpty()) {
            return personAndFireStationNumberWhoServedHimArrayList;
        }
        for (Person person : personListByAddress) {
            Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findAllByFirstnameAndLastname(person.getFirstName(), person.getLastName());
            Age CalculateAge = new Age(medicalRecord.get().getBirthdate(), "MM/dd/yyyy");
            int age = calculateAgeService.CalculateAge(CalculateAge);
            Optional<FireStation> getFireStation = fireStationRepository.findFireStationByAddress(person.getAddress());
            PersonsAndFireStationWhoDeservedThem personsAndFireStationWhoDeservedThem = new PersonsAndFireStationWhoDeservedThem();
            personsAndFireStationWhoDeservedThem.setFirstName(person.getFirstName());
            personsAndFireStationWhoDeservedThem.setLastName(person.getLastName());
            personsAndFireStationWhoDeservedThem.setPhoneNumber(person.getPhone());
            personsAndFireStationWhoDeservedThem.setAge(age);
            personsAndFireStationWhoDeservedThem.setMedications(medicalRecord.get().getMedications());
            personsAndFireStationWhoDeservedThem.setAllergies(medicalRecord.get().getAllergies());
            personsAndFireStationWhoDeservedThem.setFireStationNumber(getFireStation.get().getStation());
            personAndFireStationNumberWhoServedHimArrayList.add(personsAndFireStationWhoDeservedThem);
        }
        return personAndFireStationNumberWhoServedHimArrayList;
    }
}











