package com.project.apisafetynet.model;
import lombok.Data;

import java.util.List;

@Data
public class HouseHold {

    private String address;
    private List<HouseHoldMember> houseHoldMemberList;


    public HouseHold(String address, List<HouseHoldMember> houseHoldMemberList) {
        this.address = address;
        this.houseHoldMemberList = houseHoldMemberList;
    }

    public HouseHold(String address) {
        this.address = address;
    }
}
