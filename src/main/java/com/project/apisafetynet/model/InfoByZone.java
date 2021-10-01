package com.project.apisafetynet.model;

import lombok.Data;
import java.util.List;


@Data
public class InfoByZone {

    List<PersonCoveredByFireStation> personCoveredByFireStation;
    long adults;
    long child;
}
