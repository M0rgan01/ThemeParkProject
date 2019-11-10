package com.theme.park.object;

import com.theme.park.entities.Park;
import com.theme.park.entities.SocialUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private Long id;
    private String content;
    private Date date;
    private int notation;
    private SocialUser socialUser;
    private Park park;
}
