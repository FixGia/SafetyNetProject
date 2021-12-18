package com.project.apisafetynet.Service;

import com.project.apisafetynet.Repository.MedicalRecordRepository;
import com.project.apisafetynet.model.DTO.MedicalRecordRequest;
import com.project.apisafetynet.model.ModelRepository.Allergies;
import com.project.apisafetynet.model.ModelRepository.MedicalRecord;
import com.project.apisafetynet.model.ModelRepository.Medications;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
@Slf4j
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordServiceImpl(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    @Override
    public void saveMedicalRecord(List<MedicalRecord> medicalRecordList) {
        this.medicalRecordRepository.saveAll(medicalRecordList);
    }

    @Override
    public void deleteMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordRepository.delete(medicalRecord);
    }


    @Override
    public Optional<MedicalRecord> getMedicalRecord(String firstName, String lastName) {
        return medicalRecordRepository.findAllByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Optional<MedicalRecord> saveMedicalRecord(MedicalRecord medicalRecord) {
        try {
            List<MedicalRecord> medicalRecordToCreate = medicalRecordRepository.findMedicalRecordByFirstNameAndLastName(medicalRecord.getFirstName(),medicalRecord.getLastName());
            if (!medicalRecordToCreate.isEmpty()) {
                return Optional.empty();
            }
            medicalRecordRepository.save(medicalRecord);

        } catch (Exception e) {
            log.debug("Error attempting to add a new MedicalRecord in [MedicalRecord/saveMedicalRecord");
        }
        return Optional.of(medicalRecord);
    }

    @Override
    public MedicalRecord updateMedicalRecord(MedicalRecordRequest medicalRecord, String firstName, String lastName) {

        Optional<MedicalRecord> mR = medicalRecordRepository.findAllByFirstNameAndLastName(firstName, lastName);
        if (mR.isPresent()) {
            MedicalRecord currentMedicalRecord = mR.get();

            String firstname = medicalRecord.getFirstName();
            if (firstname != null) {
                currentMedicalRecord.setFirstName(firstname);
            }
            String lastname = medicalRecord.getLastName();
            if (lastname != null) {
                currentMedicalRecord.setLastName(lastname);
            }
            String birthdate = medicalRecord.getBirthdate();
            if (birthdate != null) {
                currentMedicalRecord.setBirthdate(birthdate);
            }
            List<Medications> medications = medicalRecord.getMedications();
            if (medications != null) {
                currentMedicalRecord.setMedications(medications);
            }

            List<Allergies> allergies = medicalRecord.getAllergies();
            if (allergies != null) {
                currentMedicalRecord.setAllergies(allergies);
            }
            medicalRecordRepository.save(currentMedicalRecord);
            return currentMedicalRecord;
        }
        log.debug("Error attempting to update a MedicalRecord in [MedicalRecord/updateMedicalRecord");
        return mR.get();
    }

    @Override
    public MedicalRecord createMedicalRecord(MedicalRecordRequest medicalRecordRequest) {

        Optional<MedicalRecord> searchMedicalRecord =
                medicalRecordRepository.findAllByFirstNameAndLastName(medicalRecordRequest.getFirstName(), medicalRecordRequest.getLastName());
        if(!searchMedicalRecord.isPresent()){
            MedicalRecord createMedicalRecord = new MedicalRecord();
            createMedicalRecord.setFirstName(medicalRecordRequest.getFirstName());
            createMedicalRecord.setLastName(medicalRecordRequest.getLastName());
            createMedicalRecord.setBirthdate(medicalRecordRequest.getBirthdate());
            createMedicalRecord.setMedications(medicalRecordRequest.getMedications());
            createMedicalRecord.setAllergies(medicalRecordRequest.getAllergies());
            medicalRecordRepository.save(createMedicalRecord);
            return createMedicalRecord;
        }
        log.error("medicalRecord already exist");
        return null;
    }

}

