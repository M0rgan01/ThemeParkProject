package com.theme.park.object;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Représente les données d'un commentaire")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentDTO {

    @ApiModelProperty(notes = "Identifiant du commentaire", example = "1L")
    private Long id;

    @ApiModelProperty(notes = "Contenu du commentaire", example = "Superbe parc !")
    @Size(max = 1000, message = "comment.content.max.value.not.correct")
    @NotBlank(message = "comment.content.null")
    private String content;

    @ApiModelProperty(notes = "Date de création du commentaire")
    private Date date;

    @ApiModelProperty(notes = "Note du commentaire", example = "4")
    @Min(value = 0, message = "comment.notation.min.value.not.correct")
    @Max(value = 5, message = "comment.notation.max.value.not.correct")
    private int notation;

    private SocialUserDTO socialUser;

    private ParkDTO park;

    @JsonIgnore
    public ParkDTO getPark() {
        return park;
    }

    @JsonProperty
    public void setPark(ParkDTO park) {
        this.park = park;
    }
}
