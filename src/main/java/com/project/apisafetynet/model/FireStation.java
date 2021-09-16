package com.project.apisafetynet.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class FireStation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @Column
    public String address;
    @Column
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
