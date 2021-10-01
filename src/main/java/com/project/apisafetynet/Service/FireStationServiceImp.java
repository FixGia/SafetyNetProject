package com.project.apisafetynet.Service;
import com.project.apisafetynet.Repository.FireStationRepository;
import com.project.apisafetynet.Repository.MedicalRecordRepository;
import com.project.apisafetynet.Repository.PersonRepository;
import com.project.apisafetynet.model.*;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
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
        int adults = 0;
        int child = 0;
        for (FireStation fireStation : getFireStationNumber) {
            ArrayList<Person> personArrayList = personRepository.findPersonByAddress(fireStation.getAddress());
            for (Person person : personArrayList) {
                PersonCoveredByFireStation personCoveredByStation = new PersonCoveredByFireStation();
                    personCoveredByStation.setFirstName(person.getFirstName());
                    personCoveredByStation.setLastName(person.getLastName());
                    personCoveredByStation.setAddress(person.getAddress());
                    personCoveredByStation.setPhone(person.getPhone());
                    personListByStation.add(personCoveredByStation);
                    ArrayList<MedicalRecord> medicalRecordsList = medicalRecordRepository.findMedicalRecordByFirstnameAndLastname(personCoveredByStation.getFirstName(), personCoveredByStation.getLastName());
                    for (MedicalRecord medicalRecord : medicalRecordsList) {
                        String birthDate = medicalRecord.getBirthdate();
                        Age CalculateAge = new Age(birthDate, "MM/dd/yyyy");
                        int age = calculateAgeService.CalculateAge(CalculateAge);
                        if (age < 18 ) {
                            child++;
                        } else {
                            adults++;
                        }
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

    public ArrayList<Flood> getListOfFloodsByStations (@NotNull ArrayList<String> stations) {
        ArrayList<Flood> floodArrayList = new ArrayList<>();
        for (String station : stations) {
        ArrayList<FireStation> getFireStation= fireStationRepository.findFireStationByStation(station);
            if (!getFireStation.isEmpty()) {
                for (FireStation fireStation : getFireStation) {
                    ArrayList<Person> getPersonByAddress= personRepository.findPersonByAddress(fireStation.getAddress());
                    ArrayList<FloodMembers> FloodMemberArrayList = buildFloodMemberList(getPersonByAddress);
                    Flood flood = new Flood(fireStation.getAddress(), FloodMemberArrayList);
                    floodArrayList.add(flood);
                }
            }
        }
        return floodArrayList;
    }

    public ArrayList<FloodMembers> buildFloodMemberList(@NotNull ArrayList<Person> getPersonByAddress) {

        ArrayList<FloodMembers> floodMembersArrayList= new ArrayList<>();
        for ( Person person : getPersonByAddress) {
            Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findAllByFirstnameAndLastname(person.getFirstName(), person.getLastName());
            FloodMembers floodMembers = new FloodMembers();
            Age CalculateAge = new Age(medicalRecord.get().getBirthdate(), "MM/dd/yyyy");
            int age = calculateAgeService.CalculateAge(CalculateAge);
            floodMembers.setFirstName(person.getFirstName());
            floodMembers.setLastName(person.getLastName());
            floodMembers.setPhone(person.getPhone());
            floodMembers.setAge(age);
            floodMembers.setMedications(medicalRecord.get().getMedications());
            floodMembers.setAllergies(medicalRecord.get().getAllergies());
            floodMembersArrayList.add(floodMembers);
        }
        return floodMembersArrayList;
    }

}











