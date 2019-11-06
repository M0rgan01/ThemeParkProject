package com.theme.park.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    /**
     * Convertie une liste de String en liste de GrantedAuthority
     *
     * @param roles --> liste de String
     * @return liste de GrantedAuthority
     */
    public static List<GrantedAuthority> getListAuthorities(List<String> roles) {

        if (roles == null || roles.isEmpty())
            throw new IllegalArgumentException("user.list.roles.empty");

        List<GrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return authorities;
    }

    /**
     * Récupère une liste de GrantedAuthority à partir de claims
     *
     * @param claims --> claims contenant une liste de role
     * @return liste de GrantedAuthority
     */
    public static List<GrantedAuthority> getListAuthorities(Collection<Role> roles) {

        if (roles == null || roles.isEmpty())
            throw new IllegalArgumentException("list.roles.empty");

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }
}
