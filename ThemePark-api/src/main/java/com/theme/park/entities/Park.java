package com.theme.park.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Park {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String urlName;
    private float globalNotation;
    private String location;
    private String gps;
    private int attractionNumber;
    private String officialUrl;
    private String opening;
    private Date dateCreation;
    private Date openingDate;
    @OneToMany(mappedBy = "park")
    private List<Comment> comments;
    @ElementCollection(targetClass=String.class)
    private List<String> photoList;
    @ManyToOne
    private Country country;
}
