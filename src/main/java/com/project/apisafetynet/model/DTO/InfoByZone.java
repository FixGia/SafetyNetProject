package com.project.apisafetynet.model.DTO;

import lombok.Data;
import java.util.List;


@Data
public class InfoByZone {

    List<PersonCoveredByFireStation> personCoveredByFireStation;
    long adults;
    long child;
}
