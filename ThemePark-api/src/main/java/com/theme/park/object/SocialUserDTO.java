package com.theme.park.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SocialUserDTO {

    private Long socialUserId;
    private String id;
    private String provider;
    private String name;
    private String email;
    private String photoUrl;
    private String firstName;
    private String lastName;
    private boolean active;
}
