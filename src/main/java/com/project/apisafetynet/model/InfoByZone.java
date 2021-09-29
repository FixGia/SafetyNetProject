package com.project.apisafetynet.model;

import lombok.Data;
import java.util.ArrayList;


@Data
public class InfoByZone {

    ArrayList<PersonCoveredByFireStation> personCoveredByFireStation;
    int adults;
    int child;
}
