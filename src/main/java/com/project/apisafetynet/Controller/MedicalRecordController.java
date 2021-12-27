package com.project.apisafetynet.Controller;

import com.project.apisafetynet.Service.MedicalRecordService;
import com.project.apisafetynet.model.ModelRepository.MedicalRecord;
import com.project.apisafetynet.model.DTO.MedicalRecordRequest;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/medicalRecord")
@Slf4j
public class MedicalRecordController {

    private static final String CLASSPATH = "com.project.ApiSafetyNet.MedicalRecordController";
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    /**
     *
     * Get one MedicalRecord Object by firstName and lastName
     * @param
     * @return a MedicalRecord Object
     */
    @ApiOperation(" get medicalrecord")
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

            log.error("fail to get "+ firstName+" "+ lastName+ "'s MedicalRecord");
            return  new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }
    /**
     * Create a medical Record
     * @param medicalRecord  a medicalRecord object
     * @param
     * @return Create a medicalRecord
     */
    @ApiOperation(" create medicalrecord")
    @PostMapping()
    public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestBody MedicalRecordRequest medicalRecord) {

        String functionPath = CLASSPATH + "createMedicalRecord";
        log.info("Request received in" + functionPath);

        try {
            log.info("Request is a success");
            MedicalRecord createMedicalRecord = medicalRecordService.createMedicalRecord(medicalRecord);
            return new ResponseEntity<>(createMedicalRecord, HttpStatus.CREATED);
        } catch (Exception exception) {
            log.error("Fail to save new MedicalRecord ");
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
    @ApiOperation("update medicalrecord")
    @PutMapping()
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestParam("firstName")String firstName,@RequestParam("lastName") String lastName,@RequestBody MedicalRecordRequest medicalRecord ) {

        String functionPath = CLASSPATH + "updateMedicalRecord";
        log.info("Request received in"+ functionPath);


        MedicalRecord mR = medicalRecordService.updateMedicalRecord(medicalRecord, firstName, lastName);
        if (mR!= null) {

            MedicalRecord medicalRecordToUpdate= mR;
            log.info("Request is a success"+mR+" is updated");
            return new ResponseEntity<>(medicalRecordToUpdate, HttpStatus.OK);
        }

            log.error(" Fail to update "+ firstName+" "+ lastName+"'s medicalRecord because it doesn't exist");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }
    /**
     * Delete MedicalRecord by firstName + lastName
     * @param
     *
     * @return Delete MedicalRecord
     */
    @ApiOperation(" delete medicalrecord")
    @DeleteMapping()
    public ResponseEntity<MedicalRecord> deleteMedicalRecord(@RequestParam("firstName") String firstName, @RequestParam("lastName")String lastName) {
        String functionPath = CLASSPATH + "deleteMedicalRecord";
        log.info("Request received in" + functionPath);

        Optional<MedicalRecord> mR = medicalRecordService.getMedicalRecord(firstName, lastName);

        if (mR.isPresent()) {

            log.info("Request is success " + firstName + " " + lastName + " is deleted");
            MedicalRecord medicalRecordToDelete = mR.get();
            medicalRecordService.deleteMedicalRecord(medicalRecordToDelete);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
            log.error("Fail to delete" + firstName + " " + lastName + " MedicalRecord because it doesn't exist in Db");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);


    }

}
