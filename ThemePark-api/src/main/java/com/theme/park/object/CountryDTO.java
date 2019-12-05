package com.theme.park.object;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonIgnore
    private List<ParkDTO> parks;

}
