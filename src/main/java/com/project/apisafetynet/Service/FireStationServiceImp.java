package com.project.apisafetynet.Service;

import com.project.apisafetynet.Repository.FireStationRepository;
import com.project.apisafetynet.model.Firestation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

}