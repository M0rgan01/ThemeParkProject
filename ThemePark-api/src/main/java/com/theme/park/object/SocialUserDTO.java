package com.theme.park.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.theme.park.entities.Role;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Représente les données d'un utilisateur")
public class SocialUserDTO {

    private Long socialUserId;
    @NotBlank(message = "user.id.empty")
    private String id;
    @NotBlank(message = "user.provider.empty")
    private String provider;
    private String name;
    @NotBlank(message = "user.email.empty")
    private String email;
    private String photoUrl;
    private String firstName;
    private String lastName;
    private boolean active;
    private Collection<Role> roles;
}
