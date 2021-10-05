package com.project.apisafetynet.model.ModelRepository;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class FireStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String address;
    public String station;



    public FireStation( String address,
                        String station) {
        this.address = address;
        this.station = station;
    }

    public FireStation() {
    }

    public FireStation address(String address) {
        this.address = address;
        return this;
    }
    public FireStation station(String station) {
        this.station= station;
        return this;
    }
    public FireStation build () {
        return new FireStation(address,station);
    }

}
