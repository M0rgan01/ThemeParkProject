package com.theme.park.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Représente les données d'un parc")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkDTO {

    @ApiModelProperty(notes = "Identifiant du parc", example = "1L")
    private Long id;

    @ApiModelProperty(notes = "Nom du parc", example = "Europa park", required = true)
    @NotBlank(message = "park.name.empty")
    @Size(max = 100, message = "park.name.max.value.not.correct")
    private String name;

    @ApiModelProperty(notes = "Nom du parc, format URL", example = "Europa park", readOnly = true)
    private String urlName;

    @ApiModelProperty(notes = "Notation global du parc", example = "3.6", readOnly = true)
    @Min(value = 0, message = "park.notation.min.value.not.correct")
    @Max(value = 5, message = "park.notation.max.value.not.correct")
    private float globalNotation;

    @ApiModelProperty(notes = "Pays du parc", example = "France", required = true)
    @NotNull(message = "park.country.null")
    private CountryDTO country;

    @ApiModelProperty(notes = "Adresse du parc", example = "France", required = true)
    @Size(max = 255, message = "park.location.max.value.not.correct")
    @NotBlank(message = "park.location.empty")
    private String location;

    @Size(max = 50, message = "park.gps.max.value.not.correct")
    @ApiModelProperty(notes = "Coordonées GPS du parc", example = "45.927028, 4.806917")
    private String gps;

    @ApiModelProperty(notes = "Nombre d'attraction du parc du parc", example = "15")
    @Max(value = 999, message = "park.attraction.number.max.value")
    private int attractionNumber;

    @ApiModelProperty(notes = "URL officiel du parc", example = "https://www.europapark.de/fr")
    @Size(max = 255, message = "park.url.max.value.not.correct")
    private String officialUrl;

    @ApiModelProperty(notes = "Information sur l'ouverture du parc", example = "Ouvert toute l'année")
    private String opening;

    @ApiModelProperty(notes = "URL des photos du parc")
    private List<String> photoList;

    @ApiModelProperty(notes = "Date de création du parc sur l'application", readOnly = true)
    private Date dateCreation;

    @ApiModelProperty(notes = "Date de création du parc")
    private Date openingDate;

}
