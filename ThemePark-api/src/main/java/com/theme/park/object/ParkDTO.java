package com.theme.park.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @ApiModelProperty(notes = "Notation global du parc", example = "3.6", required = true)
    @Min(value = 0, message = "park.notation.min.value.not.correct")
    @Max(value = 5, message = "park.notation.max.value.not.correct")
    private double globalNotation;

    @ApiModelProperty(notes = "Pays du parc", example = "France", required = true)
    @Size(max = 50, message = "park.country.max.value.not.correct")
    @NotBlank(message = "park.country.empty")
    private String country;

    @ApiModelProperty(notes = "Adresse du parc", example = "France", required = true)
    @Size(max = 100, message = "park.location.max.value.not.correct")
    @NotBlank(message = "park.location.empty")
    private String location;

    @Size(max = 50, message = "park.gps.max.value.not.correct")
    @ApiModelProperty(notes = "Coordonées GPS du parc", example = "45.927028, 4.806917")
    private String GPS;

    @ApiModelProperty(notes = "Nombre d'attraction du parc du parc", example = "15")
    private int attractionNumber;

    @ApiModelProperty(notes = "URL officiel du parc", example = "https://www.europapark.de/fr")
    @Size(max = 255, message = "park.url.max.value.not.correct")
    private String URL;

    @ApiModelProperty(notes = "URL de l'image du drapeau du pays", example = "https://www.countryflags.io/be/flat/64.png")
    @Size(max = 255, message = "park.url.flag.max.value.not.correct")
    @NotBlank(message = "park.url.flag.empty")
    private String URLFlag;

    @ApiModelProperty(notes = "Information sur l'ouverture du parc", example = "Ouvert toute l'année")
    private String opening;

    @ApiModelProperty(notes = "URL des photos du parc")
    private List<String> photoList;

    @ApiModelProperty(notes = "Date de création du parc sur l'application")
    private Date dateCreation;

    @ApiModelProperty(notes = "Date de création du parc")
    private Date openingDate;
}
