package com.theme.park.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Country {


    public Country(Long id, String countryNameEn, String code) {
        this.id = id;
        this.countryNameEn = countryNameEn;
        this.code = code;
    }

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String countryNameEn;
    private String countryNameFr;
    private String code;
    @JsonIgnore
    @OneToMany(mappedBy = "country")
    private List<Park> parks;
}
