package com.theme.park.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Park {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double globalNotation;
    private String country;
    private String location;
    private String GPS;
    private int attractionNumber;
    private String URL;
    private String opening;
    @OneToMany(mappedBy = "park")
    private List<Comment> comments;
}
