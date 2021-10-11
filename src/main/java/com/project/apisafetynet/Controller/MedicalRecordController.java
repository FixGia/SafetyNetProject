package com.project.apisafetynet.Controller;

import com.project.apisafetynet.Service.MedicalRecordService;
import com.project.apisafetynet.model.ModelRepository.Allergies;
import com.project.apisafetynet.model.ModelRepository.MedicalRecord;
import com.project.apisafetynet.model.ModelRepository.Medications;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("medicalRecords")
public class MedicalRecordController {

    final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    /**
     * Get all MedicalRecords
     * @return all MedicalRecords
     */
    @GetMapping
    public Iterable<MedicalRecord> getMedicalRecords() {
        return medicalRecordService.getMedicalRecords();
    }

    /**
     *
     * Get one MedicalRecord Object by firstname and lastname
     * @param Id is firstname + lastname
     * @return a MedicalRecord Object
     */
    @GetMapping("{Id}")
    public Optional<MedicalRecord> getMedicalRecord(@PathVariable("Id")String Id){
        return medicalRecordService.getMedicalRecord(Id);
    }
    /**
     * Create a medical Record
     * @param medicalRecord  a medicalRecord object
     * @param Id is firstname + lastname
     * @return Create a medicalRecord
     */
    @PostMapping("{Id}")
    public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord, @PathVariable("Id") String Id) {
        Optional<MedicalRecord> mR= medicalRecordService.getMedicalRecord(Id);
        if (mR.isEmpty()) {
            MedicalRecord currentMedicalRecord = medicalRecordService.saveMedicalRecord(medicalRecord);
            return currentMedicalRecord;
        } else {
            return null;
        }
    }
    /**
     * Updated MedicalRecord Object
     * @param medicalRecord a medicalRecord object
     * @param Id is firstname + lastname
     * @return A medicalRecord Object updated
     */
    @PutMapping("{Id}")
    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord, @PathVariable("Id")String Id) {
        Optional<MedicalRecord> mR = medicalRecordService.getMedicalRecord(Id);
        if (mR.isPresent()) {
            MedicalRecord currentMedicalRecord = mR.get();

            String firstname = medicalRecord.getFirstname();
            if( firstname != null) {
                currentMedicalRecord.setFirstname(firstname);
            }
            String lastname = medicalRecord.getLastname();
            if ( lastname != null) {
                currentMedicalRecord.setLastname(lastname);
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
            medicalRecordService.saveMedicalRecord(currentMedicalRecord);
            return currentMedicalRecord;
        } else {
            return null;
        }
    }
    /**
     * Delete MedicalRecord by firstname + lastname
     * @param Id is firstname + lastname
     * @param medicalRecord a medicalRecord object
     * @return Delete MedicalRecord
     */
    @DeleteMapping("{Id}")
    public MedicalRecord deleteMedicalRecord(@PathVariable("Id") String Id, MedicalRecord medicalRecord) {
        medicalRecordService.getMedicalRecord(Id);
      return medicalRecordService.deleteMedicalRecord(medicalRecord);
    }

}
