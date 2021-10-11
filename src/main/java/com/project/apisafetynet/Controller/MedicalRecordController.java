package com.project.apisafetynet.Controller;

import com.project.apisafetynet.Service.MedicalRecordService;
import com.project.apisafetynet.model.ModelRepository.MedicalRecord;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping("/medicalRecords")
    public Iterable<MedicalRecord> getMedicalRecords() {
        return medicalRecordService.getMedicalRecords();
    }
    /**
     *
     * Get one MedicalRecord Object by firstname and lastname
     * @param
     * @return a MedicalRecord Object
     */
    @GetMapping()
    public Optional<MedicalRecord> getMedicalRecord (@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName){
        return medicalRecordService.getMedicalRecord(firstName,lastName);
    }
    /**
     * Create a medical Record
     * @param medicalRecord  a medicalRecord object
     * @param
     * @return Create a medicalRecord
     */
    @PostMapping()
    public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        Optional<MedicalRecord> mR= medicalRecordService.getMedicalRecord(firstName,lastName);
        if (mR.isEmpty()) {
            return medicalRecordService.saveMedicalRecord(medicalRecord);
        } else {
            return null;
        }
    }
    /**
     * Updated MedicalRecord Object
     * @param medicalRecord a medicalRecord object
     * @param firstName  firstname of Person's MedicalRecord
     * @param lastName lastname of Person's MedicalRecord
     * @return A medicalRecord Object updated
     */
    @PutMapping()
    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord, @RequestParam("firstName")String firstName, @RequestParam("lastName") String lastName) {
        Optional<MedicalRecord> mR = medicalRecordService.getMedicalRecord(firstName, lastName);
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
            String medications = medicalRecord.getMedications();
            if (medications != null) {
                currentMedicalRecord.setMedications(medications);
            }
            String allergies = medicalRecord.getAllergies();
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
     * @param
     * @param medicalRecord a medicalRecord object
     * @return Delete MedicalRecord
     */
    @DeleteMapping()
    public MedicalRecord deleteMedicalRecord(@RequestParam("firstName") String firstName, @RequestParam("lastName")String lastName, MedicalRecord medicalRecord) {
        medicalRecordService.getMedicalRecord(firstName, lastName);
        medicalRecordService.deleteMedicalRecord(medicalRecord);
        return medicalRecord;
    }

}
