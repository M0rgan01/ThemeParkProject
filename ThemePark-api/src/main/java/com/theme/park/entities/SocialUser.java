package com.theme.park.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialUser {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long socialUserId;
    private String id;
    private String provider;
    private String name;
    private String email;
    private String photoUrl;
    private String firstName;
    private String lastName;
    private Date firstLoginDate;
    private boolean active;
    @OneToMany(mappedBy = "socialUser")
    private List<Comment> comments;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new HashSet<>();
    @Transient
    private List<GrantedAuthority> authorities;
}
