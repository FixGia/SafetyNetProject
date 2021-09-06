package com.project.apisafetynet.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Firestation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @Column
    public String address;
    @Column
    public String station;



    public Firestation( String address,
                        String station) {
        this.address = address;
        this.station = station;
    }

    public Firestation() {
    }

    public Firestation address(String address) {
        this.address = address;
        return this;
    }
    public Firestation station(String station) {
        this.station= station;
        return this;
    }
    public Firestation build () {
        return new Firestation(address,station);
    }

}
