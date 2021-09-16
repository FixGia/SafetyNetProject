package com.project.apisafetynet.Service;
import com.project.apisafetynet.Repository.FireStationRepository;
import com.project.apisafetynet.model.FireStation;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class FireStationServiceImp implements FireStationService {

    final FireStationRepository fireStationRepository;

    public FireStationServiceImp(FireStationRepository fireStationRepository) {
        this.fireStationRepository = fireStationRepository;
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
    public Optional<FireStation> getFireStation(long id) {
       return this.fireStationRepository.findById(id);
    }

    @Override
    public void deleteFireStation(long id) {
        this.fireStationRepository.deleteById(id);
    }

}
