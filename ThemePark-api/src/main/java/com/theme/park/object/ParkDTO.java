package com.theme.park.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkDTO {
    private Long id;
    @Length(max = 100)
    private String name;
    @Min(0)
    @Max(5)
    private double globalNotation;
    @Length(max = 50)
    private String country;
    @Length(max = 100)
    private String location;
    private String GPS;
    private int attractionNumber;
    @Length(max = 255)
    private String URL;
    private String opening;
    private Date dateCreation;
    private Date openingDate;
}
