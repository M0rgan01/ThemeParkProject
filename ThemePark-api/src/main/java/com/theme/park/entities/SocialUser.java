package com.theme.park.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialUser {

    @Id
    private Long id;
    private String provider;
    private String name;
    private String email;
    private String photoUrl;
    private String firstName;
    private String lastName;
    private String authToken;
    private String idToken;
    private String authorizationCode;
    @OneToMany(mappedBy = "socialUser")
    private List<Comment> comments;
    @ManyToMany
    private Collection<Role> roles = new HashSet<>();
}
