package com.project.apisafetynet.model;

import lombok.Data;

import java.util.List;

@Data
public class Flood {

    private String address;
    List<FloodMembers> floodMembersList;

    public Flood(String address, List<FloodMembers> floodMembersList) {
        this.address = address;
        this.floodMembersList = floodMembersList;
    }
}
