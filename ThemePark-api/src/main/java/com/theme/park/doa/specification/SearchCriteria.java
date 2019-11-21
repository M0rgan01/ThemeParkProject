package com.theme.park.doa.specification;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

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

    public static List<SearchCriteria> convertBase64Url(String base64Url, ObjectMapper mapper) throws IOException {
        byte[] bValues = Base64.getDecoder().decode(base64Url.getBytes());
        String json = new String(bValues);
        return mapper.readValue(json, new TypeReference<List<SearchCriteria>>(){});
    }

}
