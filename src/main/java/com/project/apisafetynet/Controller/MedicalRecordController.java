package com.project.apisafetynet.Controller;

import com.project.apisafetynet.Service.MedicalRecordService;
import com.project.apisafetynet.model.ModelRepository.Allergies;
import com.project.apisafetynet.model.ModelRepository.MedicalRecord;
import com.project.apisafetynet.model.ModelRepository.Medications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medicalRecord")
@Slf4j
public class MedicalRecordController {

    private static final String CLASSPATH = "com.project.ApiSafetyNet.MedicalRecordController";
    final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    /**
     *
     * Get one MedicalRecord Object by firstName and lastName
     * @param
     * @return a MedicalRecord Object
     */
    @GetMapping()
    public ResponseEntity<MedicalRecord> getMedicalRecord (@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName){
        String functionPath = CLASSPATH + "getMedicalRecord";
        log.info("Request received in"+ functionPath);

        Optional<MedicalRecord> getMedicalRecord = medicalRecordService.getMedicalRecord(firstName,lastName);
        if ( getMedicalRecord.isPresent()) {
            log.info("Request is success");
            MedicalRecord medicalRecord = getMedicalRecord.get();
            return new ResponseEntity<>(medicalRecord, HttpStatus.OK);
        }
        else {
            log.error("fail to get "+ firstName+" "+ lastName+ "'s MedicalRecord");
            return  new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    /**
     * Create a medical Record
     * @param medicalRecord  a medicalRecord object
     * @param
     * @return Create a medicalRecord
     */
    @PostMapping()
    public ResponseEntity<MedicalRecord> createMedicalRecord(MedicalRecord medicalRecord, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        String functionPath = CLASSPATH + "createMedicalRecord";
        log.info("Request received in"+ functionPath);
        Optional<MedicalRecord> mR= medicalRecordService.getMedicalRecord(firstName,lastName);
        if (mR.isEmpty()) {
            log.info("Request is a success");
            medicalRecordService.saveMedicalRecord(medicalRecord);
            return new ResponseEntity<>(medicalRecord, HttpStatus.CREATED);
        } else {
            log.error("Fail to save"+firstName+ lastName+" because is already exist");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Updated MedicalRecord Object
     * @param medicalRecord a medicalRecord object
     * @param firstName  firstName of Person's MedicalRecord
     * @param lastName lastName of Person's MedicalRecord
     * @return A medicalRecord Object updated
     */
    @PutMapping()
    public ResponseEntity<MedicalRecord> updateMedicalRecord(MedicalRecord medicalRecord, @RequestParam("firstName")String firstName, @RequestParam("lastName") String lastName) {

        String functionPath = CLASSPATH + "updateMedicalRecord";
        log.info("Request received in"+ functionPath);

        Optional<MedicalRecord> mR = medicalRecordService.updateMedicalRecord(medicalRecord, firstName, lastName);
        if (mR.isPresent()) {
            MedicalRecord medicalRecordToUpdate= mR.get();
            log.info("Request is a success"+mR+" is updated");
            return new ResponseEntity<>(medicalRecordToUpdate, HttpStatus.OK);
        } else {
            log.error(" Fail to update "+ firstName+" "+ lastName+"'s medicalRecord because it doesn't exist");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    /**
     * Delete MedicalRecord by firstName + lastName
     * @param
     * @param medicalRecord a medicalRecord object
     * @return Delete MedicalRecord
     */
    @DeleteMapping()
    public ResponseEntity<MedicalRecord> deleteMedicalRecord(@RequestParam("firstName") String firstName, @RequestParam("lastName")String lastName, MedicalRecord medicalRecord) {
        String functionPath = CLASSPATH + "deleteMedicalRecord";
        log.info("Request received in" + functionPath);
        Optional<MedicalRecord> mR = medicalRecordService.getMedicalRecord(firstName, lastName);

        if (mR.isPresent()) {

            log.info("Request is success " + firstName + " " + lastName + " is deleted");
            MedicalRecord medicalRecordToDelete = mR.get();
            medicalRecordService.deleteMedicalRecord(medicalRecordToDelete);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            log.error("Fail to delete" + firstName + " " + lastName + " MedicalRecord because it doesn't exist in Db");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

}
