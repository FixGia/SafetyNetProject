package com.project.apisafetynet.Service.Impl;
import com.project.apisafetynet.Repository.FireStationRepository;
import com.project.apisafetynet.Repository.MedicalRecordRepository;
import com.project.apisafetynet.Repository.PersonRepository;
import com.project.apisafetynet.Service.CalculateAgeService;
import com.project.apisafetynet.Service.FireStationService;
import com.project.apisafetynet.model.DTO.*;
import com.project.apisafetynet.model.DTO.FirestationRequest;
import com.project.apisafetynet.model.ModelRepository.FireStation;
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
public class FireStationServiceImp implements FireStationService {

    private final FireStationRepository fireStationRepository;
    private final PersonRepository personRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final CalculateAgeService calculateAgeService;


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
    public Optional<FireStation> saveFireStation(FireStation fireStation) {
        try {
            Optional<FireStation> fireStationToCreate = fireStationRepository.findById(fireStation.getId());
            if (fireStationToCreate.isPresent()){
                return Optional.empty();
            }
            fireStationRepository.save(fireStation);
        } catch (Exception e) {
            log.error("Error attempting to add a new FireStation in [FireStation/saveFireStation]");
        }
        return  Optional.of(fireStation);

    }
    public FireStation createFireStation(FirestationRequest firestationRequest) {

        Optional<FireStation> searchFirestation = fireStationRepository.findFireStationByAddress(firestationRequest.getAddress());
        if (!searchFirestation.isPresent()) {
            FireStation createFirestation = new FireStation();
            createFirestation.setStation(firestationRequest.getStation());
            createFirestation.setAddress(firestationRequest.getAddress());
            fireStationRepository.save(createFirestation);
            return createFirestation;
        }
        log.error("firestation with {} already exist", firestationRequest.getAddress());
        return null;
    }
        @Override
    public Iterable<FireStation> getFireStations() {
        return this.fireStationRepository.findAll();
    }

    @Override
    public Optional<FireStation> getFireStation(long id) {
        return this.fireStationRepository.findById(id);
    }

    public FireStation getFirestationByAddress(String address){
       Optional<FireStation> fireStation =  fireStationRepository.findFireStationByAddress(address);
        return fireStation.get();
    }

    @Override
    public void deleteFireStation(Long id) {
        this.fireStationRepository.deleteById(id);
    }

    /**
     * Method which used to get a list Of Person Information By station
     * @param station FireStation's number
     * @return A List of every person's covered by station's number with their information plus child's number and adult's number
     */
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
                    List<MedicalRecord> medicalRecordsList = medicalRecordRepository.findMedicalRecordByFirstNameAndLastName(personCoveredByStation.getFirstName(), personCoveredByStation.getLastName());
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

    /**
     *
     * @param station
     * @return
     */
    public ArrayList<PhoneAlert> getPhoneNumberByStation(String station) {

        ArrayList<FireStation> getFireStationNumber = fireStationRepository.findFireStationByStation(station);
        ArrayList<PhoneAlert> personPhoneNumberByStation = new ArrayList<>();
        for (FireStation fireStation : getFireStationNumber) {
            ArrayList<Person> personArrayList = personRepository.findPersonByAddress(fireStation.getAddress());
            for (Person person : personArrayList) {
                PhoneAlert phoneNumberByStation = new PhoneAlert();
                phoneNumberByStation.setPhone(person.getPhone());
                personPhoneNumberByStation.add(phoneNumberByStation);

            }
        }
        return personPhoneNumberByStation;
    }

    /**
     *
     * @param address
     * @return
     */
    public ArrayList<PersonsAndFireStationWhoDeservedThem> getPersonListAndStationNumber(String address) {
        ArrayList<Person> personListByAddress = personRepository.findPersonByAddress(address);
        ArrayList<PersonsAndFireStationWhoDeservedThem> personAndFireStationNumberWhoServedHimArrayList = new ArrayList<>();
        if (personListByAddress.isEmpty()) {
            return personAndFireStationNumberWhoServedHimArrayList;
        }
        for (Person person : personListByAddress) {
            Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findAllByFirstNameAndLastName(person.getFirstName(), person.getLastName());
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

    /**
     *
     * @param stations
     * @return
     */
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

    @Override
    public Optional<FireStation> updateFireStation(Long id, FirestationRequest fireStation) {

        Optional<FireStation> f = fireStationRepository.findById(id);
        if (f.isPresent()) {
            FireStation currentFireStation = f.get();

            String address = fireStation.getAddress();
            if (address != null) {
                currentFireStation.setAddress(address);
            }
            String station = fireStation.getStation();
            if (station != null) {
                currentFireStation.setStation(station);
            }
            fireStationRepository.save(currentFireStation);
        }
        return f;
    }
    /**
     *
     * @param getPersonByAddress
     * @return
     */
    public ArrayList<FloodMembers> buildFloodMemberList(@NotNull ArrayList<Person> getPersonByAddress) {

        ArrayList<FloodMembers> floodMembersArrayList= new ArrayList<>();
        for ( Person person : getPersonByAddress) {
            Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findAllByFirstNameAndLastName(person.getFirstName(), person.getLastName());
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











