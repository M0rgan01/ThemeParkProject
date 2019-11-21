package com.theme.park.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.theme.park.entities.Park;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Représente les données d'un pays")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryDTO {

    private Long id;
    private String countryNameEn;
    private String countryNameFr;
    private String code;
    private List<Park> parks;

}
