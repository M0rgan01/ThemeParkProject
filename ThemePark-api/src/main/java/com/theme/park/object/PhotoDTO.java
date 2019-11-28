package com.theme.park.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Représente les photos d'un parc")
public class PhotoDTO {

    @ApiModelProperty(notes = "Identifiant de la photo")
    private Long id;
    @ApiModelProperty(notes = "Chemin de la photo")
    private String path;
    @ApiModelProperty(notes = "Nom de la photo")
    private String name;

}
