package com.project.apisafetynet.model.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChildrenAndFamilyMembers {

    private List<Child> childrenArrayList;
    private List<FamilyMembers> OtherMembersFamilyList;

    public ChildrenAndFamilyMembers(ArrayList<Child> childrenArrayList, ArrayList<FamilyMembers> OthersMembersFamilyList) {
        this.childrenArrayList = childrenArrayList;
        OtherMembersFamilyList = OthersMembersFamilyList;
    }
    public ChildrenAndFamilyMembers() {
    }
}
