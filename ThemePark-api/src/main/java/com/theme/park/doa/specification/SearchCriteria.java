package com.theme.park.doa.specification;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Représente les données permettant la recherche précise d'un park")
public class SearchCriteria {
    @ApiModelProperty(notes = "clé de l'attribut", example = "quantity", required = true)
    private String key;
    @ApiModelProperty(notes = "Opération à éffectuée, égalité -> :, supérieur ou égal -> >=, supérieur -> >, inférieur ou égal -> <=," +
            " inférieur -> <, ordonné croissant -> ORDER_BY_ASC, ordonné décroissant -> ORDER_BY_DESC", example = ":", required = true)
    private String operation;
    @ApiModelProperty(notes = "Valeur de l'attribut", example = "12", required = true)
    private Object value;
}
