package com.project.apisafetynet.Service;
import com.project.apisafetynet.Repository.FireStationRepository;
import com.project.apisafetynet.model.FireStation;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Data
public class FireStationServiceImp implements FireStationService {

    final FireStationRepository fireStationRepository;

    public FireStationServiceImp(FireStationRepository fireStationRepository) {
        this.fireStationRepository = fireStationRepository;
    }


    @Override
    public void saveFireStationService(List<FireStation> FireStationList) {
        this.fireStationRepository.saveAll(FireStationList);
    }
}